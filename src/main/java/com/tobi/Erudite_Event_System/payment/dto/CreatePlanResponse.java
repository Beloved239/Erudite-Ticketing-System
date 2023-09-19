package com.tobi.Erudite_Event_System.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@lombok.Data
public class CreatePlanResponse {

    private Boolean status;
    private String message;
    private Data data;


}

