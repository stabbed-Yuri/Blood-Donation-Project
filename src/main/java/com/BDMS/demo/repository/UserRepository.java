package com.BDMS.demo.repository;

import com.BDMS.demo.persistent.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    List<UserEntity> findByBloodType(String bloodType);


    List<UserEntity> findByLocationContainingIgnoreCaseAndBloodType(String location, String bloodType);
}

