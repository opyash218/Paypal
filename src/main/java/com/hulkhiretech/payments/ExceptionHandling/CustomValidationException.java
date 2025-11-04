package com.hulkhiretech.payments.ExceptionHandling;


import lombok.Getter;

@Getter
public class CustomValidationException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
