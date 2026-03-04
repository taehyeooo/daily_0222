package com.example.daily0303.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/product/list").permitAll()
                        .requestMatchers("/product/add").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/member/list").hasRole("ADMIN")
                        .requestMatchers("/member/detail").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login.defaultSuccessUrl("/"))
                .logout(logout -> logout.logoutSuccessUrl("/"));

        return http.build();
    }
}
