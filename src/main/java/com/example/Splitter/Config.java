package com.example.Splitter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class Config {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())                   // H2 console needs this
                .headers(AbstractHttpConfigurer::disable)  // enable frames
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**","/swagger-ui/**").permitAll()     // allow H2
                        .anyRequest().permitAll()
                )
                .build();
    }
}
