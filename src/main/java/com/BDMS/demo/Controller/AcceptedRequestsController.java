package com.BDMS.demo.Controller;

import com.BDMS.demo.Service.RecipientService;
import com.BDMS.demo.Service.UserService;
import com.BDMS.demo.persistent.RecipientEntity;
import com.BDMS.demo.persistent.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class AcceptedRequestsController {

    private final RecipientService recipientService;
    private final UserService userService; // Add UserService for fetching current user

    public AcceptedRequestsController(RecipientService recipientService, UserService userService) {
        this.recipientService = recipientService;
        this.userService = userService; // Initialize UserService
    }

    @GetMapping("/accepted")
    public String showAcceptedRequests(Model model, Principal principal) {
        // Get the current user
        UserEntity currentUser = userService.findByUsername(principal.getName());

        // Fetch accepted requests for the user
        List<RecipientEntity> acceptedRequests = recipientService.findAcceptedRequestsByUser(currentUser);

        // Add to the model
        model.addAttribute("acceptedRequests", acceptedRequests);

        // Return the view
        return "accepted-requests";
    }
}

