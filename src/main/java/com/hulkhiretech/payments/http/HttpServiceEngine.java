package com.hulkhiretech.payments.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class HttpServiceEngine {

	private final RestClient restClient;

	public ResponseEntity<String> makeHttpCall(HttpRequest httpRequest) {
		log.info("Making HTTP call in HttpServiceEngine");

		try {
			ResponseEntity<String> httpResponse = restClient
					.method(httpRequest.getHttpMethod())
					.uri(httpRequest.getUrl())
					.headers(
							restClientHeaders -> 
							restClientHeaders.addAll(
									httpRequest.getHttpHeaders()))
					.body(httpRequest.getBody())
					.retrieve()
					.toEntity(String.class);

			log.info("HTTP call completed httpResponse:{}", httpResponse);

			return httpResponse;
		}
        catch (HttpClientErrorException | HttpServerErrorException e) {
            // Extract status and response body (JSON)
            int statusCode = e.getStatusCode().value();
            String responseBody = e.getResponseBodyAsString();

            log.error("HTTP Error: {} - Response: {}", statusCode, responseBody);

            // Handle 503/504 specifically
            if (statusCode == 503 || statusCode == 504) {
                throw new RuntimeException("Service Unavailable (no response) - " + statusCode + ": " + responseBody, e);
            }

            return ResponseEntity
                    .status(HttpStatus.valueOf(statusCode))
                    .body(responseBody);


        }

        catch (Exception e) {
			log.error("Exception while preparing form data: {}", e.getMessage(), e);

			throw new RuntimeException("HTTP call failed in HttpServiceEngine"
					+ ": " + e.getMessage());
		}
	}
}
