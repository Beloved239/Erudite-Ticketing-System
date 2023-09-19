package com.tobi.Erudite_Event_System.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventData {
    private String ticketType;
    private Integer price;
    private Integer quantity;
    private Integer subTotal;
}
