package com.Parfetch.ParFetch.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.io.IOException;

public class SenderAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SenderAuthenticationFilter(AuthenticationManager authManager) {
        super("/receiver-login"); // This must match the <form action="/sender-login">
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        // Ensure UTF-8 encoding for form input
        request.setCharacterEncoding("UTF-8");

        String phoneNumber = request.getParameter("username"); // Student logs in with phone as username
        if (phoneNumber == null) phoneNumber = "";
        phoneNumber = phoneNumber.trim();

        // Authenticate with just phone number (no password)
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(phoneNumber, null);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        // ✅ Persist authentication in the session context
        SecurityContextHolder.getContext().setAuthentication(authResult);

        request.getSession().setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        // ✅ Redirect to student home
        response.sendRedirect("/student-home");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              org.springframework.security.core.AuthenticationException failed)
            throws IOException {
        // Optional: log the failure
        System.out.println("❌ Receiver login failed: " + failed.getMessage());

        response.sendRedirect("/login?error=true");
    }
}
