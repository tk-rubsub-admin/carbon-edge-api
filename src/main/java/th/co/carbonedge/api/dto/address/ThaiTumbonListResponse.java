package th.co.carbonedge.api.dto.address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated(value = "manual", date = "2026-04-05", comments = "Thai address DTO")
public class ThaiTumbonListResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ThaiTumbon> data = new ArrayList<>();
}
