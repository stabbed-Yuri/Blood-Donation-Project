package com.BDMS.demo.Service.impl;

import org.springframework.stereotype.Service;

import com.BDMS.demo.exception.ResourceNotFoundException;
import com.BDMS.demo.persistent.UserEntity;
import com.BDMS.demo.repository.UserRepository;
import com.BDMS.demo.Service.UserServiceNew;


import java.util.List;

@Service
public class UserServiceImpl implements UserServiceNew {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity saveEntity(UserEntity userEntity){

        return userRepository.save(userEntity);
    }


    @Override
    public List<UserEntity> getAllUsers(){

        return userRepository.findAll();
    }
}
