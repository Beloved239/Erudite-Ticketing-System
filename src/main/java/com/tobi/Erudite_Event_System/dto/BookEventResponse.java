package com.tobi.Erudite_Event_System.dto;

import com.tobi.Erudite_Event_System.payment.dto.InitializePaymentResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEventResponse {
    private String responseCode;
    private String responseMessage;

    private EventData data;
    private InitializePaymentResponse response;

}
