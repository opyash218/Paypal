package com.hulkhiretech.payments.ExceptionHandling;


import com.hulkhiretech.payments.Req.UserOrderRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator {


    public void validate(UserOrderRequestDto req) {
        validateCurrency(req.getCurrencyCode());
        validateAmount(req.getValue());
        validateUrl(req.getReturnUrl());
        validateCancelUrl(req.getCancelUrl());
    }

    private void validateCurrency(String currency) {
        if (currency == null || currency.trim().isEmpty() ) {
            throw new CustomValidationException(ErrorCode.INVALID_CURRENCY, "Unknown PayPal error occurred");
        }
        if (!currency.equals("USD")) {
            throw new CustomValidationException(ErrorCode.INVALID_CURRENCY_TYPE, "Unknown PayPal error occurred");

        }
    }

    private void validateAmount(String amount) {

        if (amount == null || amount.isBlank()) {
            throw new CustomValidationException(ErrorCode.INVALID_AMOUNT_NULL, "Unknown PayPal error occurred");
        }
        double amt= Double.parseDouble(amount);
        if ( amt <= 0 )  {
            throw new CustomValidationException(ErrorCode.INVALID_AMOUNT, "Unknown PayPal error occurred");
        }
    }

    private void validateUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new CustomValidationException(ErrorCode.INVALID_URL, "Unknown PayPal error occurred");
        }
    }

    private void validateCancelUrl(String cancelUrl) {
        if (cancelUrl == null || cancelUrl.trim().isEmpty()) {
            throw new CustomValidationException(ErrorCode.INVALID_CANCEL_URL, "Unknown PayPal error occurred");
        }
    }
}
