package com.Parfetch.ParFetch.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

   // @GetMapping("/index")
    //public String staffDashboard(Authentication authentication, Model model) {
       // model.addAttribute("username", authentication.getName());
        //return "index"; // Will render src/main/resources/templates/index.html
   // }

        @GetMapping("/index")
        public String redirectBasedOnRole(Authentication authentication, Model model) {
            model.addAttribute("username", authentication.getName());

            boolean isReceiver = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_RECEIVER"));

            if (isReceiver) {
                return "redirect:/student-home";
            } else {
                return "index"; // Renders templates/index.html
            }
        }

        //@GetMapping("/student-home")
        //public String showStudentHome(Authentication authentication, Model model) {
           // model.addAttribute("username", authentication.getName());
            //return "student-home"; // Renders templates/student-home.html
       // }
    }



