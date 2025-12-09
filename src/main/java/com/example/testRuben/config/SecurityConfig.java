package com.example.testRuben.config;

import com.example.testRuben.service.OAuth2UserServiceCustom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final OAuth2UserServiceCustom oAuth2UserServiceCustom;
    private final JwtFilter jwtFilter;

    public SecurityConfig(OAuth2UserServiceCustom oAuth2UserServiceCustom, JwtFilter jwtFilter) {
        this.oAuth2UserServiceCustom = oAuth2UserServiceCustom;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/auth/**", "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/auth/success", true)
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(oAuth2UserServiceCustom) // ✔ referencia al bean inyectado
                        )
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

