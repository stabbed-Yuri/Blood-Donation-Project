package com.BDMS.demo.Controller;

import com.BDMS.demo.repository.UserRepository;
import com.BDMS.demo.persistent.UserEntity;
import com.BDMS.demo.Service.UserService;
//import com.BDMS.demo.Service.UserServiceNew;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/signUp")
public class UserController {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserService userService;


    @GetMapping
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("success", false);
        return "/signUp"; // Render sign-up page
    }

    @PostMapping
    public String handleSignUp(@Valid @ModelAttribute("user") UserEntity user, BindingResult bindingResult, Model model) {
        // Check if there were any validation errors in the user form
        if (bindingResult.hasErrors()) {
            return "/signUp"; // If validation fails, return to sign-up page
        }

        // Check if the email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("emailError", "Email is already registered");
            return "/signUp"; // If email is taken, return to sign-up page with an error
        }

        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("usernameError", "Username is already taken");
            return "/signUp"; // If username is taken, return to sign-up page with an error
        }

        // Encode the password using BCryptPasswordEncoder before saving it to the database
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); // Set the encoded password
        userRepository.save(user); // Save the user to the database

        model.addAttribute("success", true); // Add success message to the model
        return "redirect:/login"; // Redirect to login page after successful sign-up
    }

}
