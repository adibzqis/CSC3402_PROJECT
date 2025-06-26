package com.Parfetch.ParFetch.service;

import com.Parfetch.ParFetch.repository.ReceiverRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final ReceiverRepository receiverRepository;

    public CustomUserDetailsService(ReceiverRepository receiverRepository) {
        this.receiverRepository = receiverRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String cleanedUsername = username.trim().toLowerCase();
        System.out.println("🔐 Attempting staff login with username: " + cleanedUsername);

        if ("staff".equals(cleanedUsername)) {
            return User.withUsername("staff")
                    .password("$2a$10$6nEYVIrH0.TCMSPTUwOehuna0N3wlgwYCQhEh6TWcwgDmocNT4pzC") // password: staff123
                    .roles("STAFF")
                    .build();
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
