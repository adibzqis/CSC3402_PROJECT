package com.Parfetch.ParFetch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @GetMapping("/dashboard")
    public String staffDashboard() {
        return "staff"; // You can create this HTML or use existing staff pages
    }
}
