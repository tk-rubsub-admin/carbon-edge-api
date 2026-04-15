package th.co.carbonedge.api.dto.address;

import java.io.Serializable;

import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated(value = "manual", date = "2026-04-05", comments = "Thai address DTO")
public class ThaiTumbon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 20)
    private String code;

    @NotBlank
    @Size(max = 20)
    private String thaiAmphureCode;

    @Size(max = 150)
    private String nameTh;

    @Size(max = 150)
    private String nameEn;

    @Size(max = 20)
    private String postalCode;
}
