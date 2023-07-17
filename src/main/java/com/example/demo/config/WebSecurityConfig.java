package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.config.converter.JwtAuthConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public static final String ADMIN = "admin";
    public static final String USER = "user";
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, "/storages", "/storages/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/storages", "/storages/**").hasRole(ADMIN)
            .requestMatchers(HttpMethod.DELETE, "/storages", "/storages/**").hasRole(ADMIN)
            .requestMatchers(HttpMethod.GET, "/storages/test", "/storages/test/**").hasAnyRole(ADMIN, USER)
            .requestMatchers(HttpMethod.GET, "/storages/test2", "/storages/test2/**").permitAll()
            .anyRequest().authenticated();
        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthConverter);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}
