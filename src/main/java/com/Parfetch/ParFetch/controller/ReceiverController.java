package com.Parfetch.ParFetch.controller;

import com.Parfetch.ParFetch.model.Parcel;
import com.Parfetch.ParFetch.model.Receiver;
import com.Parfetch.ParFetch.service.ReceiverService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ReceiverController {

    private final ReceiverService receiverService;

    public ReceiverController(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }
    @GetMapping("/student-home")
    public String studentHome(Model model, Authentication authentication) {
        String phoneNumber = authentication.getName();
        Receiver receiver = receiverService.findByPhone(phoneNumber);

        if (receiver != null) {
            model.addAttribute("username", receiver.getName());
        } else {
            model.addAttribute("username", "Student");
        }

        return "student-home";
    }


}


