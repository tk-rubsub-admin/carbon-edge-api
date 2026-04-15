package th.co.carbonedge.api.common;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import th.co.carbonedge.api.dto.ErrorResponse;

@Component
public class ErrorResponseFactory {

    public ErrorResponse build(String code, String message, List<String> details) {
        return new ErrorResponse()
            .code(code)
            .message(message)
            .timestamp(OffsetDateTime.now())
            .details(details);
    }
}
