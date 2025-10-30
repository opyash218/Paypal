package com.hulkhiretech.payments.controller;

import com.hulkhiretech.payments.Req.UserOrderRequestDto;
import com.hulkhiretech.payments.res.PayPalMinimalResponseDto;
import com.hulkhiretech.payments.res.PayPalOrderResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.service.interfaces.PaymentService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity< PayPalMinimalResponseDto> createOrder(@RequestBody UserOrderRequestDto userRequest) {

        // TODO once the request & response is finalize, update this logic

        log.info("Creating order in PayPal provider service");


        PayPalMinimalResponseDto response = paymentService.createOrder(userRequest);
        log.info("Order creation response from service: {}", response);
        return ResponseEntity.ok(response);
    }



}
