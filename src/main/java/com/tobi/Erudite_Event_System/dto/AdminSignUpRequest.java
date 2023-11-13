package com.tobi.Erudite_Event_System.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "Signup",
        description = "Schema to hold Admin information"
)
public class AdminSignUpRequest {

    @Schema(
            description = "Name of the customer", example = "Dollar Bank"
    )
    @NotNull(message = "Name should not be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;
    @Schema(
            description = "Email address of the customer", example = "oluwatobi@dollarbank.com"
    )
    @NotNull(message = "Email should not be empty")
    @Email(message = "Email address should be valid")
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "08112345678"
    )
    @NotNull(message = "Phone number should not be empty")
    @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits")
    private String phoneNumber;

    @Schema(
            description = "Dollar Bank branch address", example = "123 NewYork"
    )
    @NotEmpty(message = "Branch Address should not be null or empty")
    private String address;

    @NotNull(message = "password should not be empty")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+*=!-_])(?!.*\\s).{8,16}$",
            message = """
                    password must meet the following conditions:
                    must include at least an upper and lowercase character;
                    must include at least one special character;
                    must not include a white space character;
                    length must be between 8 and 16 characters.
                    """
    )
    private String password;

}
