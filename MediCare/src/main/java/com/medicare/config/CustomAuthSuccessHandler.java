package com.medicare.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String redirectUrl = "/dashboard";

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            redirectUrl = "/usuarios";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("MEDICO"))) {
            redirectUrl = "/citas";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("PACIENTE"))) {
            redirectUrl = "/citas";
        }

        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}
