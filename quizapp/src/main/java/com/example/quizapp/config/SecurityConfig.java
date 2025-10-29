package com.example.quizapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // disable CSRF for simplicity
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login", 
                    "/signup", 
                    "/register",
                    "/css/**", 
                    "/js/**", 
                    "/images/**"
                ).permitAll() // ✅ allow public pages
                .anyRequest().permitAll() // ✅ allow everything else (no 403s)
            )
            .formLogin(form -> form.disable()) // ✅ disable Spring Security’s login form
            .httpBasic(httpBasic -> httpBasic.disable()) // ✅ disable HTTP basic auth
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
