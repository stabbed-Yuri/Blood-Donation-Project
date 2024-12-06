package com.BDMS.demo;

import com.BDMS.demo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 'username' here will be the email provided during login
        com.BDMS.demo.User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Return a Spring Security User with email as username
        return new User(
                user.getEmail(),
                user.getPassword(),
                true, true, true, true,
                new ArrayList<>()
        );
    }
}
