package com.tobi.Erudite_Event_System.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscoverEventResponse {
    private String message;
    private List<EventResponse> eventResponses;
}
