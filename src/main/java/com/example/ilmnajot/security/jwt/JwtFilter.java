package com.example.ilmnajot.security.jwt;

import com.example.ilmnajot.service.UserDetailsServiceCustom;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsServiceCustom userDetailsServiceCustom;
    private final JwtProvider jwtProvider;

    public JwtFilter(UserDetailsServiceCustom userDetailsServiceCustom, JwtProvider jwtProvider) {
        this.userDetailsServiceCustom = userDetailsServiceCustom;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");
            if (jwtProvider.validateToken(token)) {
                String usernameFromToken = jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsServiceCustom.loadUserByUsername(usernameFromToken);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getUsername(), new ArrayList<>()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                filterChain.doFilter(request, response);
                logger.info("token successfully validated for user: {} ");
            } else {
                logger.warn("token validation received");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
