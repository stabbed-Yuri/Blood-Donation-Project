package com.BDMS.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // To hash and compare passwords

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm()); // Add LoginForm to the model
        System.out.println("Rendering login page"); // Debugging line
        return "login"; // Render login.html
    }

    @PostMapping("/login")
    public String handleLogin(LoginForm loginForm, Model model, HttpSession session) {
        String username = loginForm.getUsername(); // Use username instead of email
        String password = loginForm.getPassword();

        System.out.println("Login attempt: Username = " + username + ", Password = " + password);

        // Fetch user by username
        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("No user found with username: " + username);
            return "redirect:/login?error=true";
        }

        System.out.println("User found: " + user.getUsername());

        // Check password
        if (passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Password match successful!");
            session.setAttribute("user", user); // Save user to session
            return "redirect:/homePage"; // Redirect to home page
        } else {
            System.out.println("Password mismatch!");
            return "redirect:/login?error=true"; // Redirect on failure
        }
    }





}
