package com.BDMS.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final CustomUserDetailsService userDetailsService;

    public ProfileController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        // Get the currently authenticated user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Use the username to fetch user details
        User user = (User) userDetailsService.loadUserByUsername(username);

        // Add user details to the model to make them accessible in the view
        model.addAttribute("user", user);

        return "/profile"; // Name of the Thymeleaf HTML file for the profile page
    }
}