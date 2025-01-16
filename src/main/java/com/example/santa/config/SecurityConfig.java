package com.example.santa.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/login/**", "/css/**", "/js/**", "/images/**", "/assets2/**", "/register", "/find/**","/api/login", "/user", "/submit", "/register/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // 로그인 및 정적 리소스 허용

                        .requestMatchers("/home/**", "/admin/**", "/outgoing/**", "/transit/**", "/inventory/**", "/order/**", "/board/**", "/incoming/**","/notice/**","/reply/**", "/profile/**").authenticated() // 인증된 사용자만 허용
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .formLogin().disable() // Spring Security 기본 로그인 비활성화
                .logout().disable() // Spring Security 기본 로그아웃 비활성화
                .addFilterBefore(sessionAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 세션 기반 인증 필터 추가

        return http.build();
    }

    @Bean
    public OncePerRequestFilter sessionAuthenticationFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    Object user = session.getAttribute("user");
                    if (user != null) {
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }
}