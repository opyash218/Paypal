package com.hulkhiretech.payments.service.interfaces;

import com.hulkhiretech.payments.Req.PayPalOrderRequest;
import com.hulkhiretech.payments.Req.UserOrderRequestDto;
import com.hulkhiretech.payments.res.PayPalMinimalResponseDto;
import com.hulkhiretech.payments.res.PayPalOrderResponseDto;

public interface PaymentService {
	
	public PayPalMinimalResponseDto createOrder(UserOrderRequestDto userRequest);

}
