package th.co.carbonedge.api.dto.customer;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.annotation.Generated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import th.co.carbonedge.api.constant.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated(value = "manual", date = "2026-04-03", comments = "Added from openapi.yml without modifying existing code")
public class CreateCustomerRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Email
    private String email;

    private String phoneNumber;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @Size(max = 150)
    private String displayName;

    private LocalDate dateOfBirth;

    private Gender gender;
}
