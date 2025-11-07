package com.hulkhiretech.payments.ExceptionHandling;

import lombok.Data;
import java.util.List;

@Data
public class PayPalErrorResponseDto {
    private String name;
    private String message;
    private String debug_id;
    private List<ErrorDetail> details;
    private String error;
    private String error_description;

    @Data
    public static class ErrorDetail {
        private String field;
        private String value;
        private String location;
        private String issue;
        private String description;
    }

    // ✅ This method extracts a simple, meaningful message
    public String getDynamicMessage() {
        if (error_description != null && !error_description.isBlank())
            return error_description;

        if (details != null && !details.isEmpty()) {
            ErrorDetail d = details.get(0);
            String field = d.getField();

            // Extract short field name if present
            if (field != null && field.contains("/")) {
                String[] parts = field.split("/");
                for (int i = parts.length - 1; i >= 0; i--) {
                    if (!parts[i].isBlank() && !parts[i].contains("@")) {
                        field = parts[i].replace("value", "amount").trim();
                        break;
                    }
                }
            }

            String desc = d.getDescription() != null ? d.getDescription() : "";
            String issue = d.getIssue() != null ? d.getIssue() : "";

            // Clean text
            desc = desc.replace("A required field / parameter is missing.", "missing");
            if (field != null) field = field.replace("_", " ");

            // ✅ Smart formatting:
            if (field == null || field.isBlank()) {
                // no field name, return only description or issue
                return (desc.isBlank() ? issue : desc).trim();
            } else {
                // normal case: "amount is missing"
                return String.format("%s is %s", field, desc.isBlank() ? issue.toLowerCase() : desc)
                        .replaceAll("\\s+", " ")
                        .trim();
            }
        }

        if (message != null && !message.isBlank())
            return message;

        if (error != null && !error.isBlank())
            return error;

        return "Unknown PayPal error occurred.";
    }

}