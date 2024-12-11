package com.BDMS.demo.Controller;

import com.BDMS.demo.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String getProfile(Model model) {
        // Get the currently authenticated user's Authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("User is not authenticated or authentication is null");
            return "redirect:/login"; // Redirect to login if user is not authenticated
        }

        try {
            // Extract the username from the authentication object
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                System.out.println("Authenticated username: " + username);

                // Fetch user details from the principal if required
                User user = new User();
                user.setUsername(username);
                user.setPassword("[PROTECTED]"); // Example placeholder, remove or modify as needed
                model.addAttribute("user", user);

                return "profile"; // Return the name of the Thymeleaf HTML file (without the leading slash)
            } else {
                System.out.println("Principal is not an instance of UserDetails");
                return "redirect:/error"; // Redirect to an error page if principal type is unexpected
            }
        } catch (Exception ex) {
            System.out.println("Error retrieving user details: " + ex.getMessage());
            return "redirect:/error"; // Redirect to an error page if any error occurs
        }
    }
}
