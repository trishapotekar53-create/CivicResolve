package com.smartcitizen.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smartcitizen.portal.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http) throws Exception {

        http

            // =========================================
            // CSRF
            // =========================================

            .csrf(csrf -> csrf.disable())


            // =========================================
            // CORS
            // =========================================

            .cors(cors -> {})


            // =========================================
            // FORM LOGIN
            // =========================================

            .formLogin(form -> form.disable())


            // =========================================
            // HTTP BASIC
            // =========================================

            .httpBasic(basic -> basic.disable())


            // =========================================
            // JWT SESSION
            // =========================================

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )


            // =========================================
            // AUTHORIZATION
            // =========================================

            .authorizeHttpRequests(auth -> auth


                // -----------------------------------------
                // PUBLIC USER APIs
                // -----------------------------------------

                .requestMatchers(
                    "/api/users/register",
                    "/api/users/login"
                ).permitAll()


                // -----------------------------------------
                // FILE APIs
                // -----------------------------------------

                .requestMatchers(
                    "/api/files/**"
                ).permitAll()


                // -----------------------------------------
                // EVERYTHING ELSE
                // -----------------------------------------

                .anyRequest().authenticated()
            )


            // =========================================
            // JWT FILTER
            // =========================================

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );


        return http.build();
    }


    // =========================================
    // AUTHENTICATION MANAGER
    // =========================================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }


    // =========================================
    // PASSWORD ENCODER
    // =========================================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}