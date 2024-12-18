package com.BDMS.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.BDMS.demo.persistent.UserEntity;
import com.BDMS.demo.Service.UserServiceNew;

@RestController
@RequestMapping("/loginnew")
public class UserControllerNew {

    private UserServiceNew userServiceNew;


    public UserControllerNew(UserServiceNew userServiceNew){
        super();
        this.userServiceNew = userServiceNew;
    }


    @GetMapping
    public List<UserEntity> getAllUsers(){
        return userServiceNew.getAllUsers();
    }




}
