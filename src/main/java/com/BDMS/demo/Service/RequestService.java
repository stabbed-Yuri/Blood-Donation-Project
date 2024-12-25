package com.BDMS.demo.Service;

import com.BDMS.demo.persistent.RecipientEntity;
import com.BDMS.demo.persistent.UserEntity;
import com.BDMS.demo.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RecipientRepository recipientRepository;

    public List<RecipientEntity> getRequestsByUser(Long userId) {
        return recipientRepository.findByUserId(userId);
    }

    public void saveRequest(RecipientEntity recipient) {
        recipientRepository.save(recipient);
    }

    public void markRequestAsCompleted(Integer requestId, Long donorId) {
        RecipientEntity recipient = recipientRepository.findById(requestId).orElse(null);
        if (recipient != null) {
            UserEntity donor = new UserEntity();
            donor.setId(donorId);
            recipient.addAcceptedUser(donor);
            recipientRepository.save(recipient);
        }
    }

    public void closeRequest(Integer requestId) {
        RecipientEntity recipient = recipientRepository.findById(requestId).orElse(null);
        if (recipient != null) {
            recipient.setClosed(true);
            recipientRepository.save(recipient);
        }
    }
}