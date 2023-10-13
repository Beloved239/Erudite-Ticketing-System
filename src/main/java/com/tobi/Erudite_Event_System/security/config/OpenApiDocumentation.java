package com.tobi.Erudite_Event_System.security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Beloved",
                        email = "adettob@gmail.com",
                        url = "https://www.linkedin.com/in/adebanjo-oluwatobi-6bb25b156/"
                ),
                description = "OpenApi documentation for EventHub",
                title = "EventHub OpenApi Specification",
                version = "v1",
                license = @License(
                        name = "License name",
                        url = "https://url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Production ENV",
                        url = "https://erudite-ticketing-system-production.up.railway.app/"
                ),
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:80"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiDocumentation {
}
