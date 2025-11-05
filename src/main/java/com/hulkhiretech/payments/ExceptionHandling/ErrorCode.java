package com.hulkhiretech.payments.ExceptionHandling;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum ErrorCode {

    INVALID_CURRENCY("ERR_101", "Currency cannot be null or blank"),
    INVALID_CURRENCY_TYPE("ERR_102", "Currently only USD Currency is supported"),
    INVALID_AMOUNT("ERR_103", "Amount must be greater than 0"),
    INVALID_AMOUNT_NULL("ERR_104", "Amount must not be null or empty"),
    INVALID_URL("ERR_105", "Return URL cannot be null or blank"),
    INVALID_CANCEL_URL("ERR_106", "Cancel URL cannot be null or blank"),

    /**
     * PayPal dynamic error — message will be filled using the response JSON.
     * Example JSON:
     * {
     * "name": "VALIDATION_ERROR",
     * "message": "The value of field 'amount' is invalid.",
     * "debug_id": "...",
     * "details": [ ... ]
     * }
     */
    INVALID_PAYPAL_RESPONSE("ERR_107", "PayPal error occurred: {dynamic_message}");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * For PayPal and other external API errors,
     * replace {dynamic_message} with actual message from API.
     */
    public String formatMessage(String dynamicMessage) {
        if (dynamicMessage == null || dynamicMessage.isBlank()) {
            return message.replace("{dynamic_message}", "Unknown PayPal error");
        }

        log.info("Formatted error message: {}", dynamicMessage);
        return message.replace("{dynamic_message}", dynamicMessage);
    }
}

