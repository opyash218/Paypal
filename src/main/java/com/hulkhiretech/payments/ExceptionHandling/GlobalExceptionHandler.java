package com.hulkhiretech.payments.ExceptionHandling;





import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ErrorResponse> handleCustomValidationException(CustomValidationException ex) {
        ErrorCode error = ex.getErrorCode();
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .errorCode(error.getCode())
                        .errorMessage(error.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .errorCode("ERR_000")
                        .errorMessage("Unexpected server error")
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
