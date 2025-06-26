package com.Parfetch.ParFetch.controller;

import com.Parfetch.ParFetch.model.Receiver;
import com.Parfetch.ParFetch.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final ReceiverService receiverService;

    @Autowired
    public AuthController(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("receiver", new Receiver()); // ✅ required for Thymeleaf form
        return "register";
    }

    @PostMapping("/register")
    public String registerReceiver(@ModelAttribute Receiver receiver, Model model) {
        if (receiverService.findByPhone(receiver.getPhone()) != null) {
            model.addAttribute("error", "Phone number already registered");
            return "register";
        }
        receiver.setRole("ROLE_RECEIVER"); // ✅ Make sure role is set
        receiverService.registerReceiver(receiver);
        return "redirect:/login?registered";
    }
}
