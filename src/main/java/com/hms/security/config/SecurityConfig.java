package com.hms.security.config;

import com.hms.security.JwtAuthenticationEntryPoint;
import com.hms.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    private static final String [] PUBLIC_URLS = {
            "/auth/**","/swagger-ui/**","/v3/api-docs","/v3/api-docs/**"
            ,"/swagger-ui.html" ,"/swagger-resources", "/swagger-resources/**"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .cors()
                .disable()
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(PUBLIC_URLS).permitAll();
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling()
                .authenticationEntryPoint(point)
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
