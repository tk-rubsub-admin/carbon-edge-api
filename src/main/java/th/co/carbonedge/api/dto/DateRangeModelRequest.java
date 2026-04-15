package th.co.carbonedge.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.carbonedge.api.utils.DateUtil;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateRangeModelRequest {
    private ZonedDateTime start;
    private ZonedDateTime end;

    public ZonedDateTime getStart() {
        if (start == null) {
            return null;
        }
        return this.start.withZoneSameInstant(DateUtil.getTimeZone());
    }

    public ZonedDateTime getEnd() {
        if (end == null) {
            return null;
        }
        return this.end.withZoneSameInstant(DateUtil.getTimeZone());
    }
}
