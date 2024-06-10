package fr.isencaen.gameplatform.security;

import fr.isencaen.gameplatform.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtServiceConfig {

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }
}