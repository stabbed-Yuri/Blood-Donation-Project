package com.BDMS.demo.Controller;

import com.BDMS.demo.persistent.UserEntity;
import com.BDMS.demo.Service.UserService; // Assuming a UserService that fetches user data from the DB
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService; // Inject the service to get user data


    @PostMapping("/profile/update")// Update the user profile
    public String updateProfile(@ModelAttribute UserEntity user, RedirectAttributes redirectAttributes) {
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully.");
        return "redirect:/profile";
    }
  @PostMapping("/profile/updatePicture")
public String updateProfilePicture(@RequestParam("profilePicture") MultipartFile file, RedirectAttributes redirectAttributes) {
    System.out.println("Entering updateProfilePicture method");

    if (!file.isEmpty()) {
        String fileName = file.getOriginalFilename();
        String uploadDir = "user-photos/";
        System.out.println("File name: " + fileName);
        System.out.println("Upload directory: " + uploadDir);

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Created upload directory: " + uploadPath.toString());
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File uploaded to: " + filePath.toString());

            UserEntity user = userService.getCurrentUser(); // Assuming a method to get the current user
            System.out.println("Current user: " + user.getUsername());
            user.setProfilePicturePath(filePath.toString());
            userService.updateUser(user);
            System.out.println("User profile picture path updated");

            redirectAttributes.addFlashAttribute("message", "Profile picture updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Failed to upload profile picture.");
        }
    } else {
        System.out.println("No file selected");
        redirectAttributes.addFlashAttribute("error", "No file selected.");
    }

    System.out.println("Exiting updateProfilePicture method");
    return "redirect:/profile";
}
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

                // Fetch user details from the database using the UserService
                UserEntity user = userService.findByUsername(username); // Assuming a method like this exists

                if (user != null) {
                    model.addAttribute("user", user); // Add the user object to the model
                    return "profile"; // Return the name of the Thymeleaf HTML file (without the leading slash)
                } else {
                    System.out.println("User not found in the database");
                    return "redirect:/error"; // Redirect to error if the user is not found
                }
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
