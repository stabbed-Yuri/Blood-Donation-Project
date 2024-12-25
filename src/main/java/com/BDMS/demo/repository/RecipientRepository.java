package com.BDMS.demo.repository;

import com.BDMS.demo.persistent.RecipientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipientRepository extends JpaRepository<RecipientEntity, Integer> {

    List<RecipientEntity> findByUserId(Long userId);
}