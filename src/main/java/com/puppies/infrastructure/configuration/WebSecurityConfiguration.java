package com.puppies.infrastructure.configuration;

import com.puppies.infrastructure.Auth.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final CustomUserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfiguration(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/user/**", "/user",
                                "/swagger-ui/**", "/api-docs/**",
                                "/post/**", "/post",
                                "/like/**", "/like",
                                "/api-docs","/swagger-ui.html",
                                "/webjars/**")
                                .permitAll()
                        .anyRequest().authenticated()

                ).authenticationProvider(authenticationProvider());
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/user/**")
                .ignoringRequestMatchers("/post/**")
                .ignoringRequestMatchers("/like/**")
                .ignoringRequestMatchers("/api-docs/**")
        );
        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }
}
