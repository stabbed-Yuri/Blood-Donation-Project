package com.BDMS.demo.Service;

import com.BDMS.demo.persistent.RecipientEntity;
import com.BDMS.demo.persistent.UserEntity;
import com.BDMS.demo.repository.RecipientRepository;
import com.BDMS.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipientService {

    private final RecipientRepository recipientRepository;
    private final UserRepository userRepository;
    public List<RecipientEntity> findPendingRequestsForUser(UserEntity user) {
        // Example logic to find matching requests for the user's blood group
        return recipientRepository.findByBloodGNeededAndClosedFalse(user.getBloodType());
    }
    public void processUserResponse(Integer requestId, String response, UserEntity user) {
        RecipientEntity request = recipientRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getRespondedUsers().contains(user)) {
            throw new RuntimeException("User has already responded to this request.");
        }

        if ("accept".equalsIgnoreCase(response)) {
            request.getAcceptedUsers().add(user);
        } else if ("reject".equalsIgnoreCase(response)) {
            request.getRejectedUsers().add(user);
        }

        request.getRespondedUsers().add(user);
        recipientRepository.save(request);
    }
    public List<RecipientEntity> findPendingRequestsForUserThatUserHasNotResponded(UserEntity currentUser) {
        // Fetch all pending requests for the user (you may have a method for this)
        List<RecipientEntity> allPendingRequests = findPendingRequestsForUser(currentUser);

        // Filter out the requests that the user has already responded to
        return allPendingRequests.stream()
                .filter(request -> !hasUserResponded(request.getR_id(), currentUser.getId()))
                .collect(Collectors.toList());
    }
    public void handleUserResponse(Integer requestId, UserEntity user, String response) {
        RecipientEntity request = recipientRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getRespondedUsers().contains(user)) {
            throw new RuntimeException("User has already responded to this request.");
        }

        if ("accept".equalsIgnoreCase(response)) {
            request.getAcceptedUsers().add(user);// Add to accepted users
            request.setAcceptedCount(request.getAcceptedCount() + 1);
        } else if ("reject".equalsIgnoreCase(response)) {
            request.getRejectedUsers().add(user); // Add to rejected users
            request.setRejectedCount(
                    (request.getRejectedCount() != null ? request.getRejectedCount() : 0) + 1
            );
        }

        request.getRespondedUsers().add(user); // Track responded users
        recipientRepository.save(request); // Persist changes
    }
    @Autowired
    public RecipientService(RecipientRepository recipientRepository, UserRepository userRepository) {
        this.recipientRepository = recipientRepository;
        this.userRepository = userRepository;
    }


    public void saveRecipient(RecipientEntity recipient) {
        recipientRepository.save(recipient);
    }
    public List<RecipientEntity> findAcceptedRequestsByUser(UserEntity user) {
        return recipientRepository.findAll().stream()
                .filter(recipient -> recipient.getAcceptedUsers().contains(user))
                .toList();
    }


    public boolean hasUserResponded(Integer requestId, Long userId) {
        RecipientEntity request = recipientRepository.findById(requestId).orElse(null);
        if (request == null) {
            return false;
        }
        return request.getRespondedUsers().stream()
                .anyMatch(user -> user.getId().equals(userId));
    }

    public int getTotalMatchingDonorsNotified() {
    List<RecipientEntity> allRecipients = recipientRepository.findAll();
    int totalMatchingDonorsNotified = 0;

    for (RecipientEntity recipient : allRecipients) {
        String bloodGroupNeeded = recipient.getBlood_g_needed();
        long matchingDonorsCount = userRepository.countByBloodType(bloodGroupNeeded);
        totalMatchingDonorsNotified += (int) matchingDonorsCount;
    }

    return totalMatchingDonorsNotified;
}

}
