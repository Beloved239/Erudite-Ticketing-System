package com.tobi.Erudite_Event_System.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomEventResponse {
    private String responseCode;
    private String responseMessage;
    private List<EventResponse> eventResponse;
}
