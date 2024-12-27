package com.BDMS.demo.Service;

import com.BDMS.demo.persistent.RecipientEntity;
import com.BDMS.demo.persistent.UserEntity;
import com.BDMS.demo.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipientService {

    private final RecipientRepository recipientRepository;
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
        RecipientEntity request = recipientRepository.findById(requestId).orElseThrow();
        if ("accept".equalsIgnoreCase(response)) {
            request.addAcceptedUser(user);
        } else if ("reject".equalsIgnoreCase(response)) {
            request.setRejectedCount(request.getRejectedCount() + 1);
        }
        recipientRepository.save(request);
    }
    @Autowired
    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }


    public void saveRecipient(RecipientEntity recipient) {
        recipientRepository.save(recipient);
    }

    public boolean hasUserResponded(Integer requestId, Long userId) {
        RecipientEntity request = recipientRepository.findById(requestId).orElse(null);
        if (request == null) {
            return false;
        }
        return request.getRespondedUsers().stream()
                .anyMatch(user -> user.getId().equals(userId));
    }}
