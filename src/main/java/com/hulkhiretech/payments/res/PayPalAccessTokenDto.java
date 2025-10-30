package com.hulkhiretech.payments.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PayPalAccessTokenDto {

    @JsonProperty("access_token")
    private String accessToken;
}