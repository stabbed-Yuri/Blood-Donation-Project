package com.BDMS.demo.Service;

import java.util.List;
import com.BDMS.demo.persistent.UserEntity;
import org.springframework.security.core.userdetails.User;

public interface UserServiceNew {
    UserEntity saveEntity(UserEntity userEntity);
    List<UserEntity> getAllUsers();

}
