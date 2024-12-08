package com.BDMS.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        boolean isAuthenticated = authentication.isAuthenticated();
        if(isAuthenticated){
            setDefaultTargetUrl( "/profile");

        }
        else {
            setDefaultTargetUrl("/homePage");

        }

    }
}
