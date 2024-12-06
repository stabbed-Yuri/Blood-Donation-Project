package com.BDMS.demo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/homePage")
    public String homePage(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();  // The authenticated user's email/username
            model.addAttribute("username", username);
        }
        return "homePage"; // Replace with your actual home page view
    }
}
