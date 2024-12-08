package com.BDMS.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

        // Map roles to Spring Security authorities
       // List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
        //        .map(role -> new SimpleGrantedAuthority(role.getName())) // Assuming Role entity has a 'name' field
        //        .collect(Collectors.toList());

        // Return a Spring Security User with email as username
        Collection<? extends GrantedAuthority> authorities = List.of();
        return new User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(), // Active or not
                true, // Account not expired
                true, // Credentials not expired
                true, // Account not locked
                authorities // Granted authorities
        );
    }
}
