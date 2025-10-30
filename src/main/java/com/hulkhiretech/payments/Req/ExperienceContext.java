package com.hulkhiretech.payments.Req;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceContext {
    private String payment_method_preference;
    private String landing_page;
    private String user_action;
    private String return_url;
    private String cancel_url;
}
