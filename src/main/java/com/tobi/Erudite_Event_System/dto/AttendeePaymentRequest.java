package com.tobi.Erudite_Event_System.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "Payment Request",
        description = "Schema to hold Payment information"
)
public class AttendeePaymentRequest {
    @Schema(
            description = "Users email", example = "john@email.com"
    )
    @NotNull(message = "Email should not be empty")
    private String email;
}
