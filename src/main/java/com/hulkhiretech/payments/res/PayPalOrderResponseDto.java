package com.hulkhiretech.payments.res;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class PayPalOrderResponseDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("status")
    private String status;

//    @JsonProperty("links")
    @JsonProperty(value = "links", access = JsonProperty.Access.WRITE_ONLY)
    private List<Link> links;

    @JsonProperty("payer_action_link")
    private PayerActionLink payerActionLink;

    @Data
    public static class Link {
        private String href;
        private String rel;
        private String method;
    }

    @Data
    public static class PayerActionLink {
        private String href;
        private String rel;
        private String method;
    }
}
