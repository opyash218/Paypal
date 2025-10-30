package com.hulkhiretech.payments.Req;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayPalOrderRequest {
    private String intent;
    private List<PurchaseUnit> purchase_units;
    private PaymentSource payment_source;
}
