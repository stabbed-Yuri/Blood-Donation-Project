package com.BDMS.demo.Controller;

import com.BDMS.demo.Service.RequestService;
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

import java.security.Principal;
import java.util.List;

@Controller
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/requestSummary")
    public String getRequestSummary(Principal principal, Model model) {
        UserEntity loggedInUser = getLoggedInUser(principal);
        List<RecipientEntity> requests = requestService.getRequestsByUser(loggedInUser.getId());
        model.addAttribute("requests", requests);
        return "requestSummary";
    }

    @PostMapping("/requests/complete")
    public String markRequestAsCompleted(@RequestParam Integer requestId, @RequestParam Long donorId) {
        requestService.markRequestAsCompleted(requestId, donorId);
        return "redirect:/requestSummary";
    }

    @PostMapping("/requests/close")
    public String closeRequest(@RequestParam Integer requestId) {
        requestService.closeRequest(requestId);
        return "redirect:/requestSummary";
    }



    private UserEntity getLoggedInUser(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username);
    }
}