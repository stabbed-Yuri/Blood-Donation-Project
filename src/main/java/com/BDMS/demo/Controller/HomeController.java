package com.BDMS.demo.Controller;

import com.BDMS.demo.persistent.RecipientEntity;
import com.BDMS.demo.repository.RecipientRepository;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final RecipientRepository recipientRepository;

    public HomeController(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    @GetMapping("/homePage")
public String showHomePage(Model model) {

    List<RecipientEntity> recipients = recipientRepository.findAll();
    model.addAttribute("recipients", recipients);
    return "homePage";
}
}
