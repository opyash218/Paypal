package com.hulkhiretech.payments.Req;

import lombok.Data;



import lombok.Data;

@Data
public class UserOrderRequestDto {
    private String currencyCode;   // e.g. "USD"
    private String value;          // e.g. "230.00"
    private String returnUrl;      // dynamic return URL
    private String cancelUrl;      // dynamic cancel URL
}