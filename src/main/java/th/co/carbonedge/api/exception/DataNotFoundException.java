package th.co.carbonedge.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import th.co.carbonedge.api.constant.ErrorCode;

@Getter
public class DataNotFoundException extends BaseCheckedException {

    public DataNotFoundException(String message) {
        this(ErrorCode.DATA_NOT_FOUND, message, HttpStatus.BAD_REQUEST);
    }

    public DataNotFoundException(String code, String message) {
        this(code, message, HttpStatus.BAD_REQUEST);
    }

    public DataNotFoundException(String code, String message, HttpStatus httpStatus) {
        super(code, message, httpStatus);
    }

}
