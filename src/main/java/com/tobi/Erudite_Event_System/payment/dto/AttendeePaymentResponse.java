package com.tobi.Erudite_Event_System.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendeePaymentResponse {
    private Long id;
    private String name;
    private String email;
}
