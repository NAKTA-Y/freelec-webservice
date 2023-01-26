package com.example.freelecwebservice.config;

import com.example.freelecwebservice.config.auth.CustomOAuth2UserService;
import com.example.freelecwebservice.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                .and().and().build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and().build();
    }
}
