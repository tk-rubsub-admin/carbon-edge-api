package th.co.carbonedge.api.notifier;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import jakarta.mail.internet.MimeMessage;
import th.co.carbonedge.api.domain.Customer;

@Slf4j
@Component
@Primary
@RequiredArgsConstructor
public class SmtpCustomerEmailVerificationNotifier implements CustomerEmailVerificationNotifier {

    private final JavaMailSender mailSender;

    @Value("${app.customer.verify-email-url-base:http://localhost:3000/verify-email}")
    private String verifyBaseUrl;

    @Value("${app.customer.mail-from:no-reply@localhost}")
    private String fromEmail;

    @Value("${app.customer.mail-subject:Verify your email address}")
    private String subject;

    @Value("classpath:mail/customer-verification-email.html")
    private Resource emailTemplate;

    @Override
    public void sendVerificationEmail(Customer customer, String rawToken, OffsetDateTime expiresAt) {
        String verifyUrl = verifyBaseUrl + "?token=" + rawToken;
        String customerName = customer.getDisplayName() != null && !customer.getDisplayName().isBlank()
            ? customer.getDisplayName()
            : customer.getFirstName();

        try {
            String html = renderTemplate(Map.of(
                "customerName", customerName,
                "verifyUrl", verifyUrl,
                "expiresAt", expiresAt.toString()
            ));

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
            helper.setFrom(fromEmail);
            helper.setTo(customer.getEmail());
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send verification email to {}", customer.getEmail(), e);
            log.info(
                "Fallback verification link for {}: {} (expiresAt={})",
                customer.getEmail(),
                verifyUrl,
                expiresAt
            );
        }
    }

    private String renderTemplate(Map<String, String> values) throws IOException {
        String template;
        try (var inputStream = emailTemplate.getInputStream()) {
            template = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }

        String rendered = template;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            rendered = rendered.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return rendered;
    }
}
