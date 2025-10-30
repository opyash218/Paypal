package com.hulkhiretech.payments.res;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayPalMinimalResponseDto {
    private String id;
    private String status;
    private String href;
}