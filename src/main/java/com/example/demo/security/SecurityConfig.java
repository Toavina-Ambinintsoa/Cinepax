package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) // pas de session navigateur -> pas besoin de CSRF
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/public/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/projections/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.PUT, "/movies/**")
                    .hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PUT, "/projections/**")
                    .hasRole("MANAGER")
                    .requestMatchers(HttpMethod.GET, "/reservations")
                    .hasAnyRole("MANAGER", "EMPLOYEE") // routes ouvertes
                    .anyRequest()
                    .authenticated() // le reste demande d'être connecté
            )
        .httpBasic(Customizer.withDefaults()); // méthode d'authentification la plus simple

    return http.build();
  }
}
