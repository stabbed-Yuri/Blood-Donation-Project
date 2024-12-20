package com.BDMS.demo.Service;

import com.BDMS.demo.persistent.RecipientEntity;
import com.BDMS.demo.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipientService {

    private final RecipientRepository recipientRepository;

    @Autowired
    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public void saveRecipient(RecipientEntity recipient) {
        recipientRepository.save(recipient);
    }
}