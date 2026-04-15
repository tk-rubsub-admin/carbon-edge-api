package th.co.carbonedge.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import th.co.carbonedge.api.dto.GeneralResponse;

@Slf4j
public class SilentClientErrorHandler implements ClientErrorHandler {

    @Override
    public <T> void handleError(HttpStatusCode statusCode, GeneralResponse<T> responseBody) {
        log.warn("Http status {}\nresponse status {}\nmessage {}", statusCode, responseBody.getStatus(), responseBody.getMessage());
    }

}

