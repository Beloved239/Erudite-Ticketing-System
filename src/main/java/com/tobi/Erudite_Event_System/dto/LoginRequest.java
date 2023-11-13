package com.tobi.Erudite_Event_System.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "Login Request",
        description = "Schema to hold Login information"
)
public class LoginRequest {
    @Schema(
            description = "Users email", example = "john@email.com"
    )
    @NotNull(message = "Email should not be empty")
    private String email;

    @Schema(
            description = "Users password", example = "John@ben123"
    )
    @NotNull(message = "Name should not be empty")
    @Size(min = 8, message = "Password should be minimum of 8 characters")
    private String password;
}
