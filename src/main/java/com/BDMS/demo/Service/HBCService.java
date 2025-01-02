package com.BDMS.demo.Service;

import com.BDMS.demo.persistent.HBCEntity;
import com.BDMS.demo.repository.HBCRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Service
public class HBCService {

    @Autowired
    private HBCRepository hbcRepository; // Injecting the HBCRepository to interact with the database

    public List<HBCEntity> getAllHBCEntities() {
        return hbcRepository.findAll();
    }

    public long getTotalHBCs() {
        return hbcRepository.count();
    }
}
