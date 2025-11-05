package com.hulkhiretech.payments.ExceptionHandling;


import lombok.Getter;

@Getter
public class CustomValidationException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomValidationException(ErrorCode errorCode, String dynamicMessage) {
        super(dynamicMessage);
        this.errorCode = errorCode;
    }

}
