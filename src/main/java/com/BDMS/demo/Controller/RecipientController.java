package com.BDMS.demo.Controller;

import com.BDMS.demo.Service.RecipientService;
import com.BDMS.demo.persistent.HBCEntity;
import com.BDMS.demo.persistent.RecipientEntity;
import com.BDMS.demo.persistent.UserEntity;
import com.BDMS.demo.repository.HBCRepository;
import com.BDMS.demo.repository.UserRepository;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class RecipientController {

    private final RecipientService recipientService;
    private final UserRepository userRepository;
    private final HBCRepository hbcRepository;
    private static final Logger logger = LoggerFactory.getLogger(RecipientController.class);

    @Autowired
    public RecipientController(RecipientService recipientService, UserRepository userRepository, HBCRepository hbcRepository) {
        this.recipientService = recipientService;
        this.userRepository = userRepository;
        this.hbcRepository = hbcRepository;
    }

    @GetMapping("/formPage")
    public String showForm(Model model) {
        UserEntity loggedInUser = getLoggedInUser();
        List<HBCEntity> hbcList = hbcRepository.findAll();
        model.addAttribute("recipient", new RecipientEntity());
        model.addAttribute("user", loggedInUser);
        model.addAttribute("hbcList", hbcList);
        return "formPage";
    }

    @PostMapping("/formPage")
    public String handleFormSubmission(@ModelAttribute RecipientEntity recipient, Model model) {
        UserEntity loggedInUser = getLoggedInUser();
        Long userId = loggedInUser.getId();
        logger.info("Received userId: " + userId);
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            recipient.setUser(user);
            recipientService.saveRecipient(recipient);
            model.addAttribute("message", "Request submitted successfully!");
        } else {
            model.addAttribute("message", "User not found!");
        }
        return "homePage";
    }


    private UserEntity getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                // Assuming you have a method in UserRepository to find a user by username
                return userRepository.findByUsername(username);
            }
        }
        return null;
    }
}