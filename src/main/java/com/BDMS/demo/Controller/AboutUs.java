package com.BDMS.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUs {

    @GetMapping("/aboutUs")
    public String getAboutUs() {
        return "aboutUsPage";
    }
}
