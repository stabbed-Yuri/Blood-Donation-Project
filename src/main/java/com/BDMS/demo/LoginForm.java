package com.BDMS.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.ui.Model;

@Setter
@Getter
public class LoginForm {
    // Getters and Setters
    private String Username;
    private String password;
    private boolean rememberMe;

}
