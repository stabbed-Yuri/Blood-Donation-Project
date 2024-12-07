package com.BDMS.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.Map;

@Controller
public class NavbarController {

    // This endpoint returns the user's login status
    @GetMapping("/getUserStatus")
    @ResponseBody
    public Map<String, Object> getUserStatus(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        // Check if the user is authenticated
        if (request.getUserPrincipal() != null) {
            response.put("loggedIn", true);               // User is logged in
            response.put("username", request.getUserPrincipal().getName()); // User's name
        } else {
            response.put("loggedIn", false);              // User is not logged in
        }

        return response;  // Return the login status as JSON
    }
}
