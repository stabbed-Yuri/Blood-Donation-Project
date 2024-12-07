package com.BDMS.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService); // Set the custom UserDetailsService

        // Use BCryptPasswordEncoder for encoding and verifying passwords
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        provider.setPasswordEncoder(passwordEncoder); // Set the password encoder

        return provider;
    }







    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Allow access to public URLs (login, signup, home page, H2 console, and static resources)
                        .requestMatchers("/login", "/signUp", "/homePage", "/h2-console/**", "/static/**", "/images/**", "/css/**", "/js/**").permitAll()
                        // Require authentication for profile, logout, etc.
                        .requestMatchers("/profile", "/logout").authenticated()
                        // Protect all other URLs requiring authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username") // Change to "username" to accommodate login with username
                        .permitAll()
                        .defaultSuccessUrl("/homePage", true) // Redirect to home page after login
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error=true"))  // Custom failure handler
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // Disable CSRF only for H2 console
                )
                .headers(headers -> headers
                        .frameOptions().sameOrigin() // Allow H2 console in a frame
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService; // Attach the CustomUserDetailsService bean
    }
}
