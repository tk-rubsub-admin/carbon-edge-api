package th.co.carbonedge.api.common;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import th.co.carbonedge.api.dto.ErrorResponse;
import th.co.carbonedge.api.exception.BaseCheckedException;
import th.co.carbonedge.api.exception.DataConflictException;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.exception.ProductNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorResponseFactory errorResponseFactory;

    public GlobalExceptionHandler(ErrorResponseFactory errorResponseFactory) {
        this.errorResponseFactory = errorResponseFactory;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ProductNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(errorResponseFactory.build("PRODUCT_NOT_FOUND", exception.getMessage(), List.of()));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFound(DataNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(errorResponseFactory.build("DATA_NOT_FOUND", exception.getMessage(), List.of()));
    }

    @ExceptionHandler(BaseCheckedException.class)
    public ResponseEntity<ErrorResponse> handleBaseChecked(BaseCheckedException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
            .body(errorResponseFactory.build(exception.getCode(), exception.getMessage(), List.of()));
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(DataConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(errorResponseFactory.build("PRODUCT_CONFLICT", exception.getMessage(), List.of()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        List<String> details = exception.getBindingResult().getFieldErrors().stream()
            .map(this::formatFieldError)
            .toList();
        return ResponseEntity.badRequest()
            .body(errorResponseFactory.build("VALIDATION_ERROR", "Request validation failed", details));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException exception) {
        return ResponseEntity.badRequest()
            .body(errorResponseFactory.build(
                "VALIDATION_ERROR",
                "Request validation failed",
                exception.getConstraintViolations().stream().map(v -> v.getMessage()).toList()
            ));
    }

    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}
