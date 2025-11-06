package com.hulkhiretech.payments.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.http.HttpServiceEngine;

import com.hulkhiretech.payments.res.captureOrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceCapture {

    private final PaymentServiceImpl paymentServiceImpl;
    private final HttpServiceEngine httpServiceEngine;
    private final ObjectMapper objectMapper;





    public captureOrderResponseDto captureOrder(String orderId) {
        // Implement the logic to capture the order using the PayPal API
        // This is a placeholder implementation

        // ✅ 1. get access token from payment service impl
        String accessToken = paymentServiceImpl.getTransferToken();
        log.info("Access token in capture service: {}", accessToken);

        // ✅ 2. Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        headers.add("PayPal-Request-Id", UUID.randomUUID().toString());

        // ✅ 3. Construct capture URL
        String captureUrl = "https://api-m.sandbox.paypal.com/v2/checkout/orders/" + orderId + "/capture";
        log.info("Capture URL: {}", captureUrl);

        // ✅ 4. Build request
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setHttpMethod(HttpMethod.POST);
        httpRequest.setUrl(captureUrl);
        httpRequest.setHttpHeaders(headers);
        httpRequest.setBody(""); // No body needed for capture

        //✅ 5. Make the HTTP POST request to capture the order

        ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);
        log.info("Received response from PayPal Capture Order: {}", response);

        //✅ 6. convert response body to java object and return using object mapper
        String captureOrderRes = response.getBody();
        try {
            captureOrderResponseDto capdto = objectMapper.readValue(captureOrderRes, captureOrderResponseDto.class);
            return capdto;



        } catch (Exception e) {

            log.info("Error parsing capture order response: {}", e.getMessage());
            throw new RuntimeException();
        }

//        throw new RuntimeException("Capture order failed");



    }

}
