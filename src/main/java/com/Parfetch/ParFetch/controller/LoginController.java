package com.Parfetch.ParFetch.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {

            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {
                return "redirect:/index";
            } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RECEIVER") || a.getAuthority().equals("ROLE_STUDENT"))) {
                return "redirect:/parcels/list";
            }
        }

        return "login";
    }

}




