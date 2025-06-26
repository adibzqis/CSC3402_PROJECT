package com.Parfetch.ParFetch.service;

import com.Parfetch.ParFetch.model.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ReceiverAuthenticationProvider implements AuthenticationProvider {

    private final ReceiverService receiverService;

    @Autowired
    public ReceiverAuthenticationProvider(ReceiverService receiverService) {
        this.receiverService = receiverService; // Properly initialize studentService
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = authentication.getName();
        Receiver receiver = receiverService.findByPhone(phoneNumber);

        if (receiver == null) {
            throw new BadCredentialsException("No student found with phone number: " + phoneNumber);
        }

        return new UsernamePasswordAuthenticationToken(
                phoneNumber,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_RECEIVER"))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
