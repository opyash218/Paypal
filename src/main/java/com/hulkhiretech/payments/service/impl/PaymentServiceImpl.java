package com.hulkhiretech.payments.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulkhiretech.payments.Req.*;
import com.hulkhiretech.payments.constant.Constant;
import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.http.HttpServiceEngine;
import com.hulkhiretech.payments.res.PayPalAccessTokenDto;
import com.hulkhiretech.payments.res.PayPalMinimalResponseDto;
import com.hulkhiretech.payments.res.PayPalOrderResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.hulkhiretech.payments.service.TokenService;
import com.hulkhiretech.payments.service.interfaces.PaymentService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {



    private final TokenService tokenService;
    private final HttpServiceEngine httpServiceEngine;
    private final ObjectMapper objectMapper;
//    private final PayPalMinimalResponseDto minimalResponse;

    @Value("${createOrderUrl}")
    private String createOrderUrl;

    public PayPalMinimalResponseDto createOrder(UserOrderRequestDto userRequest) {

        log.info("Creating PayPal order for user input: {}", userRequest);

        String accessToken = tokenService.getAccessToken();
        log.info("Access token retrieved: {}", accessToken);


            // ✅ 1. Map user request -> PayPal structure
            Amount amount = new Amount(userRequest.getCurrencyCode(), userRequest.getValue());
            PurchaseUnit purchaseUnit = new PurchaseUnit(amount);

            ExperienceContext experienceContext = new ExperienceContext(
                    "IMMEDIATE_PAYMENT_REQUIRED",
                    "LOGIN",
                    "PAY_NOW",
                    userRequest.getReturnUrl(),
                    userRequest.getCancelUrl()
            );

            PayPal paypal = new PayPal(experienceContext);
            PaymentSource paymentSource = new PaymentSource(paypal);

            PayPalOrderRequest payPalOrderRequest = new PayPalOrderRequest(
                    "CAPTURE",
                    Collections.singletonList(purchaseUnit),
                    paymentSource
            );

            // ✅ 2. Convert to JSON
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(payPalOrderRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // ✅ 3. Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            headers.add("PayPal-Request-Id", UUID.randomUUID().toString());

            // ✅ 4. Build request
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.setHttpMethod(HttpMethod.POST);
            httpRequest.setUrl(createOrderUrl);
            httpRequest.setHttpHeaders(headers);
            httpRequest.setBody(requestBody);

            log.info("Prepared HttpRequest for PayPal createOrder: {}", httpRequest);

            // ✅ 5. Make call
            ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);


            String createOrderResponseBody = response.getBody();

            try {
                PayPalOrderResponseDto dto = objectMapper.readValue(createOrderResponseBody, PayPalOrderResponseDto.class);

                String sandboxLink = null;

// Extract sandbox link from links if present
                if (dto.getLinks() != null) {
                    sandboxLink = dto.getLinks().stream()
                            .filter(link -> "payer-action".equalsIgnoreCase(link.getRel()))
                            .map(PayPalOrderResponseDto.Link::getHref)
                            .findFirst()
                            .orElse(null);
                }

// Create minimal response object
                PayPalMinimalResponseDto minimalResponse = new PayPalMinimalResponseDto(
                        dto.getId(),
                        dto.getStatus(),
                        sandboxLink
                );

// Return or log minimal response
                log.info("Filtered PayPal Minimal Response: {}", objectMapper.writeValueAsString(minimalResponse));

                return minimalResponse;

            } catch (Exception e) {
                log.error("Error mapping PayPal response: {}", e.getMessage());
                throw new RuntimeException("Failed to parse PayPal order response", e);
            }





    }
}






