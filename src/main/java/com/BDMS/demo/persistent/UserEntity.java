package com.BDMS.demo.persistent;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
    @Entity
    @Table(name = "users")
    public class UserEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        private String username;

        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        private String email;

        @NotBlank(message = "Mobile number is required")
        @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Mobile number must be valid")
        private String number;

        @NotBlank(message = "Blood type is required")
        private String bloodType;

        @NotBlank(message = "Division is required")
        private String division;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        private String password;

        // New fields
        @NotBlank(message = "First name is required")
        private String firstName;

        @NotBlank(message = "Last name is required")
        private String lastName;

        @NotBlank(message = "District is required")
        private String district;

        @NotBlank(message = "Upazila is required")
        private String upazila;

        private int age;

        @NotBlank(message = "Gender is required")
        private String gender;

        private int donationsCount;

        private int completedRequests;

        private int missedRequests;

        private double responseRatio;

        private String lastDonationDate;

        @OneToMany(mappedBy = "user")
        private List<RecipientEntity> recipients;

        public boolean isEnabled() {
            return true;
        }
    }


