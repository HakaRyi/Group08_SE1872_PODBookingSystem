package com.example.POD_BookingSystem.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] AUTHORIZE_ENDPOINT = {"/api/users/customer" , "/api/auth/log-in", "/api/auth/introspect","/swagger-ui.html"};
    private final String[] AUTHORIZE_SWAGGER = {"/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"};
    private final String [] GET_ENDPOINT = {"/buildings",
            "/buildings/{name}",
            "/buildings/location/{name}",
            "/rooms","/rooms/name/{name}",
            "/rooms/building/{id}",
            "/rooms/type/{id}",
            "/rooms/status"};
    @Value("${jwt.signKey}")
    private String signerKey;


    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(
                request -> request.
                        requestMatchers(AUTHORIZE_SWAGGER).permitAll()
                        .requestMatchers(HttpMethod.POST, AUTHORIZE_ENDPOINT).permitAll()
                        .requestMatchers(HttpMethod.GET, GET_ENDPOINT).permitAll()
                        .anyRequest().permitAll()

        );
        httpSecurity.oauth2ResourceServer(
                oauth2 -> oauth2.jwt(jwtConfigurer ->
                        jwtConfigurer.decoder(jwtDecoder()))

        );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(cors -> cors.configurationSource(corsFilter()));

        return httpSecurity.build();
    }
    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");

        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build()
                ;
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public CorsConfigurationSource corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }
}