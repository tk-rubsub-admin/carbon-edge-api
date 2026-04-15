package th.co.carbonedge.api.dto.auth;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthenticatedUserResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private AuthenticatedUser data;
}
