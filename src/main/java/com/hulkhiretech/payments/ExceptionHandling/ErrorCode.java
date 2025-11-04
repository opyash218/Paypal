package com.hulkhiretech.payments.ExceptionHandling;



import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_CURRENCY("ERR_101", "Currency cannot be null or blank"),
    INVALID_CURRENCY_TYPE("ERR_102", "Currently only USD Currency is supported "),
    INVALID_AMOUNT("ERR_103", "Amount must be greater than 0"),
    INVALID_AMOUNT_NULL("ERR_104", "Amount not be null or  empty"),
    INVALID_URL("ERR_105", "Return URL cannot be null or blank"),
    INVALID_CANCEL_URL("ERR_106", "Cancel URL cannot be null or blank");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

