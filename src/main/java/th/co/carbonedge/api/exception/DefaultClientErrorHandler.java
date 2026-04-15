package th.co.carbonedge.api.exception;

import org.springframework.http.HttpStatusCode;
import th.co.carbonedge.api.dto.GeneralResponse;

public class DefaultClientErrorHandler implements ClientErrorHandler {

    @Override
    public <T> void handleError(HttpStatusCode statusCode, GeneralResponse<T> responseBody) {
        throw new ClientErrorException(
                responseBody.getStatus(),
                responseBody.getMessage(),
                statusCode
        );
    }

}

