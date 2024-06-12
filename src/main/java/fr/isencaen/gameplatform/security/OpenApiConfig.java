package fr.isencaen.gameplatform.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
//        Map<String, SecurityScheme> securitySchemeMap = new HashMap<>();
//        securitySchemeMap.put("bearerAuth", new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT")
//                .description("Example: Bearer <token-here>"));
        return new OpenAPI().info(new Info().title("Game platform API Documentation").description("Documentation of the Gaming platform project").version("v1.0"));
//                .components(new Components().securitySchemes(securitySchemeMap));
    }
}