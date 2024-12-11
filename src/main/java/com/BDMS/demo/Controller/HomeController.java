package com.BDMS.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/homePage")
    public String homePage(Model model, Principal principal) {
        // Check if user is logged in (principal will be null if not logged in)
        if (principal != null) {
            model.addAttribute("loggedInUser", principal.getName()); // Pass the username or other details
        }
        return "homePage"; // Render homepage
    }


}
