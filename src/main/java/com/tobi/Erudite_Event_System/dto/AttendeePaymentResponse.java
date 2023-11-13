package com.tobi.Erudite_Event_System.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendeePaymentResponse {
    @Schema(
            description = "Attendee id", example = "XXXYRT123"
    )
    private Long id;
    @Schema(
            description = "Attendee name", example = "John Ben"
    )
    private String name;
    @Schema(
            description = "Attendee email", example = "john@email.com"
    )
    private String email;
}
