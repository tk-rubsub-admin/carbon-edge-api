package th.co.carbonedge.api.exception;

import org.springframework.http.HttpStatusCode;
import th.co.carbonedge.api.dto.GeneralResponse;

public interface ClientErrorHandler {
    <T> void handleError(HttpStatusCode statusCode, GeneralResponse<T> body);

}
