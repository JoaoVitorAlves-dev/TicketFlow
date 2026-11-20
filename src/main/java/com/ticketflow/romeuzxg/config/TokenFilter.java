package com.ticketflow.romeuzxg.config;

import com.ticketflow.romeuzxg.service.TokenService;
import com.ticketflow.romeuzxg.service.UsuarioDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final UsuarioDetailsService usuarioDetailsService;
    private final TokenService tokenService;

    public TokenFilter(UsuarioDetailsService usuarioDetailsService, TokenService tokenService) {
        this.usuarioDetailsService = usuarioDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = extrairToken(request);

        try {
            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String token1 = tokenService.validarToken(token);
                UserDetails userDetails = usuarioDetailsService.loadUserByUsername(token1);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException |IllegalArgumentException e) {
            logger.info(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String extrairToken(HttpServletRequest req) {
        String authorization = req.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

}
