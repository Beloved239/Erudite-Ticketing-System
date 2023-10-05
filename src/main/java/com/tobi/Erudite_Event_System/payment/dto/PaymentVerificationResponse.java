package com.tobi.Erudite_Event_System.payment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVerificationResponse {

    private String status;
    private String message;
    private PaymentData data;


}

