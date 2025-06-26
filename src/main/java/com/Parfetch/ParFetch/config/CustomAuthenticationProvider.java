package com.Parfetch.ParFetch.config;

import com.Parfetch.ParFetch.model.Staff;
import com.Parfetch.ParFetch.repository.StaffRepository;
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
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final StaffRepository staffRepository;

    @Autowired
    public CustomAuthenticationProvider(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Find staff by username
        Staff staff = staffRepository.findByUsername(username);

        // Check if the provided credentials match the staff credentials
        if (staff != null && staff.getPassword().equals(password)) { // In production, use hashed password comparison
            return new UsernamePasswordAuthenticationToken(
                    username, password,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_STAFF"))
            );
        }

        throw new BadCredentialsException("Invalid staff credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
