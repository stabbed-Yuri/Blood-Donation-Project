package com.BDMS.demo.repository;

import com.BDMS.demo.persistent.HBCEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HBCRepository extends JpaRepository<HBCEntity, Integer> {
}