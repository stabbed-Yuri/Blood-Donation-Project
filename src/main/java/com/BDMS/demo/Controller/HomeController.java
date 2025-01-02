package com.BDMS.demo.Controller;

import com.BDMS.demo.Service.UserService;
import com.BDMS.demo.persistent.RecipientEntity;
import com.BDMS.demo.repository.RecipientRepository;
import com.BDMS.demo.Service.HBCService;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final RecipientRepository recipientRepository;
    private final UserService userService;
    private final HBCService hbcService;

    public HomeController(RecipientRepository recipientRepository, UserService userService, HBCService hbcService) {
        this.recipientRepository = recipientRepository;
        this.userService = userService;
        this.hbcService = hbcService;
    }

    @GetMapping("/homePage")
    public String showHomePage(Model model) {

    List<RecipientEntity> recipients = recipientRepository.findAll();
    model.addAttribute("recipient", recipients);

    long totalDonors = userService.getTotalDonors();
    model.addAttribute("totalDonors", totalDonors);

    long totalHBCs= hbcService.getTotalHBCs();
    model.addAttribute("totalHBCs", totalHBCs);

    //long totalDonations =

    return "homePage";
    }


    @GetMapping("/")
    public String redirectToHomePage(){
        return "redirect:/homePage";
    }

}
