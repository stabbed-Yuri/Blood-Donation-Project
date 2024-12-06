package com.BDMS.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/signUp")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signUp"; // Render sign-up page
    }

    @PostMapping("/signUp")
    public String handleSignUp(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        // Check if there were any validation errors in the user form
        if (bindingResult.hasErrors()) {
            return "signUp"; // If validation fails, return to sign-up page
        }

        // Check if passwords match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "signUp"; // If passwords don't match, return to sign-up page with an error
        }

        // Check if the email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("emailError", "Email is already registered");
            return "signUp"; // If email is taken, return to sign-up page with an error
        }

        // Encode the password using BCryptPasswordEncoder before saving it to the database
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); // Set the encoded password

        // Save the user to the database
        userRepository.save(user);
        return "redirect:/login"; // Redirect to login page after successful sign-up
    }
}
