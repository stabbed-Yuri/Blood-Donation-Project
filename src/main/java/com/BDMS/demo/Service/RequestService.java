package com.BDMS.demo.Service;

import com.BDMS.demo.persistent.RecipientEntity;
import com.BDMS.demo.persistent.UserEntity;
import com.BDMS.demo.repository.RecipientRepository;
import com.BDMS.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RecipientRepository recipientRepository;

    @Autowired
    private UserRepository userRepository;
    public List<RecipientEntity> getRequestsByUser(Long userId) {
    List<RecipientEntity> requests = recipientRepository.findByUserId(userId);
    for (RecipientEntity request : requests) {
        String bloodGroupNeeded = request.getBlood_g_needed();
        int matchingDonorsCount = (int) userRepository.countByBloodType(bloodGroupNeeded);
        request.setTotalMatchingDonorNotified(matchingDonorsCount);
    }
    return requests;
}

    public void saveRequest(RecipientEntity recipient) {
        recipientRepository.save(recipient);
    }

    public boolean markRequestAsCompleted(Integer requestId, Long donorId) {
        System.out.println("markRequestAsCompleted called with requestId: " + requestId + ", donorId: " + donorId);

        // Fetch the recipient request by ID
        RecipientEntity recipient = recipientRepository.findById(requestId).orElse(null);

        if (recipient == null) {
            System.out.println("Recipient not found for requestId: " + requestId);
            return false;
        }

        System.out.println("Recipient found: " + recipient.getR_id());

        // Check if the donor exists in the accepted users list
        boolean donorExists = recipient.getAcceptedUsers().stream()
                .anyMatch(user -> user.getId().equals(donorId));

        if (!donorExists) {
            System.out.println("Donor with donorId: " + donorId + " does not exist in the accepted users list.");
            return false;
        }

        // Ensure the donor has not already been marked as completed
        boolean donorAlreadyCompleted = recipient.getCompletedDonors().stream()
                .anyMatch(user -> user.getId().equals(donorId));

        if (donorAlreadyCompleted) {
            System.out.println("Donor with donorId: " + donorId + " has already been marked as completed.");
            return false;
        }

        // Fetch the donor entity
        UserEntity donor = userRepository.findById(donorId).orElse(null);
        if (donor == null) {
            System.out.println("Donor not found for donorId: " + donorId);
            return false;
        }

        // Mark the donor as completed for this request
        recipient.getCompletedDonors().add(donor);
        recipientRepository.save(recipient);

        // Increment the donor's completed requests count
        donor.setCompletedRequests(donor.getCompletedRequests() + 1);
        userRepository.save(donor);

        System.out.println("Donor marked as completed. Completed requests count updated.");
        return true;
    }


    public void closeRequest(Integer requestId) {
        RecipientEntity recipient = recipientRepository.findById(requestId).orElse(null);
        if (recipient != null) {
            recipient.setClosed(true);
            recipientRepository.save(recipient);
        }
    }

    public void updateLastDonationDate(Long donorId, Integer requestId) {
        UserEntity donor = userRepository.findById(donorId).orElseThrow(() -> new RuntimeException("Donor not found"));
        RecipientEntity recipient = recipientRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Recipient not found"));

        donor.setLastDonationDate(recipient.getRegistration_date());
        userRepository.save(donor);
    }
}