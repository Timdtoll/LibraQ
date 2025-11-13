package com.example.libraq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity // Enables Spring Security web security support
@EnableMethodSecurity
public class SecurityConfig {
	
    //BCrypt is used to hash passwords before storing them in the database.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    //Configures security filter chain that defines behaviour
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // Disable CSRF protection (required for H2 console access)
            // Cross-Site Request Forgery is a cyberattck that allows an attacker to execute unwanted actions on behalf of a user
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            // Configuring URL authorization rules
                .authorizeHttpRequests( registry -> {
                    // No authentication required for these files
                    registry.requestMatchers( "/register", "/login", "/styles.css", "/css/**", "/home", "/books", "/h2-console/**").permitAll();
                    // All other pages will require authentication
                    registry.anyRequest().authenticated();
                })
            //Form-based login
                .formLogin( formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                )
                .build();
    }
}
