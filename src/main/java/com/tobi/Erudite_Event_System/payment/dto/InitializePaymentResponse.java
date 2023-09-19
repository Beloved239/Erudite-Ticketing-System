package com.tobi.Erudite_Event_System.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializePaymentResponse {

    private Boolean status;
    private String message;
    private Data data;





}

