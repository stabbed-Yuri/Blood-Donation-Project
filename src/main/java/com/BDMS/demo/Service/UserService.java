package com.BDMS.demo.Service;

import com.BDMS.demo.persistent.UserEntity;
import com.BDMS.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // Injecting the UserRepository to interact with the database

    /**
     * Fetches the user details from the database based on the provided username.
     *
     * @param username the username of the user to be fetched
     * @return the User object containing the user's details
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    public UserEntity findByUsername(String username) {
        // Find the user by their username
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            // If the user is not found, throw an exception
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return user;
    }

    /**
     * Creates or updates a user's profile in the database.
     *
     * @param user the User object containing the updated profile details
     * @return the saved User object
     */
    public UserEntity saveUserProfile(UserEntity user) {
        // Save the user object to the database (either new or updated)
        return userRepository.save(user);
    }

    /**
     * Fetches the user by their email address.
     *
     * @param email the email of the user to be fetched
     * @return the User object containing the user's details
     * @throws UsernameNotFoundException if the user is not found with the provided email
     */
    public UserEntity findByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return user;
    }

    /**
     * Updates the password for a given user.
     *
     * @param username the username of the user whose password needs to be updated
     * @param newPassword the new password for the user
     * @return the updated User object
     * @throws UsernameNotFoundException if the user is not found with the given username
     */
    public UserEntity updatePassword(String username, String newPassword) {
        UserEntity user = findByUsername(username);
        user.setPassword(newPassword); // Update password
        return userRepository.save(user); // Save the updated user
    }

    /**
     * Deletes a user by their username.
     *
     * @param username the username of the user to be deleted
     * @throws UsernameNotFoundException if the user is not found with the given username
     */
    public void deleteUser(String username) {
        UserEntity user = findByUsername(username); // Find the user by username
        userRepository.delete(user); // Delete the user from the database
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserEntity> getUsersByBloodGroup(String bloodType) {
        return userRepository.findByBloodType(bloodType);
    }

    public List<UserEntity> searchUsersByLocationAndBloodType(String location, String bloodType) {
        return userRepository.findByLocationContainingIgnoreCaseAndBloodType(location, bloodType);
    }
}
