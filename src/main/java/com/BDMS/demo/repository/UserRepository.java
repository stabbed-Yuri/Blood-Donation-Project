package com.BDMS.demo.repository;

import com.BDMS.demo.persistent.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //@Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    UserEntity findByEmail(String email);

    //@Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    UserEntity findByUsername(String username);

    List<UserEntity> findByBloodType(String bloodType);
    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.bloodType = :bloodType")
    long countByBloodType(@Param("bloodType") String bloodType);

    //List<UserEntity> findByDivisionContainingIgnoreCaseAndBloodType(String division, String bloodType);
    List<UserEntity> findByDivisionContainingIgnoreCaseAndBloodType(String division, String bloodType);


}

