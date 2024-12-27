package com.BDMS.demo.Controller;

import com.BDMS.demo.Service.RecipientService;
import com.BDMS.demo.Service.RequestService;
import com.BDMS.demo.Service.UserService;
import com.BDMS.demo.persistent.RecipientEntity;
import com.BDMS.demo.persistent.UserEntity;
import com.BDMS.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RecipientService recipientService;

    @GetMapping("/requestSummary")
    public String getRequestSummary(Principal principal, Model model) {
        UserEntity loggedInUser = getLoggedInUser(principal);
        List<RecipientEntity> requests = requestService.getRequestsByUser(loggedInUser.getId());
        int totalMatchingDonorsNotified = recipientService.getTotalMatchingDonorsNotified();
        model.addAttribute("requests", requests);
        model.addAttribute("totalMatchingDonorsNotified", totalMatchingDonorsNotified);
        return "requestSummary";
    }


  @PostMapping("/requests/complete")
public String markRequestAsCompleted(@RequestParam Integer requestId, @RequestParam Long donorId, RedirectAttributes redirectAttributes) {
    boolean success = requestService.markRequestAsCompleted(requestId, donorId);

    if (success) {
        redirectAttributes.addFlashAttribute("message", "Donor marked as completed.");
    } else {
        redirectAttributes.addFlashAttribute("error", "Donor has already been marked as completed.");
    }
    return "redirect:/requestSummary";
}

    @PostMapping("/requests/close")
    public String closeRequest(@RequestParam Integer requestId) {
        requestService.closeRequest(requestId);
        return "redirect:/requestSummary";
    }

    @GetMapping("/pending")
    public String showPendingRequests(Model model, Principal principal) {
        // Get the current user based on their principal
        UserEntity currentUser = userService.findByUsername(principal.getName());

        // Fetch pending requests for the current user that they haven't responded to
        List<RecipientEntity> unrespondedRequests = recipientService.findPendingRequestsForUserThatUserHasNotResponded(currentUser);

        // Add the unresponded requests to the model
        model.addAttribute("requests", unrespondedRequests);
        model.addAttribute("currentUser", currentUser);
        // Return the name of the HTML template
        return "pending-requests";
    }


    @PostMapping("/respond")
    public String respondToRequest(@RequestParam("requestId") Integer requestId,
                                   @RequestParam("response") String response,
                                   Principal principal) {
        UserEntity currentUser = userService.findByUsername(principal.getName());

        // Check if the user has already responded to this request
        if (recipientService.hasUserResponded(requestId, currentUser.getId())) {
            return "redirect:/pending?error=alreadyResponded";
        }

        // Handle the user's response
        recipientService.handleUserResponse(requestId, currentUser, response);

        // Increment donations count only if the response is "accept"
        if ("accept".equalsIgnoreCase(response)) {
            currentUser.setDonationsCount(currentUser.getDonationsCount() + 1);
            userService.saveUser(currentUser);
        }

        return "redirect:/pending?success=responseRecorded";
    }




    private UserEntity getLoggedInUser(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username);
    }
}