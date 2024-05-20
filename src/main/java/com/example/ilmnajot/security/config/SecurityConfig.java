package com.example.ilmnajot.security.config;

import com.example.ilmnajot.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final AuthenticationProvider authenticationProvider;
    public SecurityConfig(JwtFilter jwtFilter, AuthenticationProvider authenticationProvider) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/**")
                        .permitAll()
                        .requestMatchers(
                                "/v2/api-docs/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/configuration/ui/**",
                                "configuration/security/**",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "swagger-ui.html/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .formLogin(login -> {
                    login.loginPage("/api/auth/login");
                    login.defaultSuccessUrl("/");
                    login.failureUrl("/login-error");
                })
                .logout(logout -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("logout"));
                    logout.logoutSuccessUrl("/");
                    logout.deleteCookies("JSESSIONID");
                    logout.invalidateHttpSession(true);
                });

        return http.build();
    }


}

