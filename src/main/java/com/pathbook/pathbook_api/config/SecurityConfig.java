package com.pathbook.pathbook_api.config;

import com.pathbook.pathbook_api.handler.GlobalAccessDeniedHandler;
import com.pathbook.pathbook_api.handler.GlobalAuthenticationEntryPoint;
import com.pathbook.pathbook_api.service.PathbookUserDetailsService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] permitAllPatterns = {
        // Auth
        "/auth/login",
        "/auth/register",
        "/auth/verify-email",
        "/auth/forgot-password",
        "/auth/reset-password",

        // Post
        "/post/list",
        "/post/p/{postId}",

        // Post Comment
        "/post/comment/p/{postId}",
        "/post/comment/c/{commentId}",

        // Proxy
        "/proxy/**",

        // User
        "/user/profile/{userId}",

        // File
        "/file/list",
        "/file/f/{filename:.+}",

        // SpringDoc OpenAPI & Swagger UI
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
    };

    @Value("#{'${server.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .cors(configurer -> configurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        (authorize) ->
                                authorize
                                        .requestMatchers(permitAllPatterns)
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .exceptionHandling(
                        exception ->
                                exception
                                        .accessDeniedHandler(globalAccessDeniedHandler())
                                        .authenticationEntryPoint(globalAuthenticationEntryPoint()))
                .logout(
                        logout ->
                                logout.logoutUrl("/auth/logout")
                                        .logoutSuccessHandler(logoutSuccessHandler())
                                        .invalidateHttpSession(true)
                                        .deleteCookies("JSESSIONID")
                                        .deleteCookies("logged_in")
                                        .clearAuthentication(true))
                .userDetailsService(userDetailsService())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(204);
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new PathbookUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // Make the below setting as * to allow connection from any host
        corsConfiguration.setAllowedOrigins(allowedOrigins);
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public AccessDeniedHandler globalAccessDeniedHandler() {
        return new GlobalAccessDeniedHandler();
    }

    public GlobalAuthenticationEntryPoint globalAuthenticationEntryPoint() {
        return new GlobalAuthenticationEntryPoint();
    }
}
