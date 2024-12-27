package com.BDMS.demo.persistent;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@Table(name = "Recipient")
public class RecipientEntity {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Integer r_id;

    @Column(name = "medical_purpose")
    private String medical_purpose;

    @Column(name = "registration_date")
    private String registration_date;

    @Column(name = "volume_needed")
    private Integer volume_needed;

    @Getter
    @Column(name = "blood_g_needed")
    private String blood_g_needed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "hbc_id", nullable = false)
    private HBCEntity hbc;

    @Getter
    @Column(name = "total_matching_donor_notified")
    private Integer totalMatchingDonorNotified;

    @Getter
    @Column(name = "accepted_count")
    private Integer acceptedCount;

    @Getter
    @Column(name = "rejected_count")
    private Integer rejectedCount;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "recipient_accepted_users",
            joinColumns = @JoinColumn(name = "recipient_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> acceptedUsers;

    @Setter
    @Column(name = "closed")
    private boolean closed;

    public void addAcceptedUser(UserEntity user) {
        this.acceptedUsers.add(user);
        this.acceptedCount = this.acceptedUsers.size();
    }

    public String getHospitalName() {
        return this.hbc.getHbc_name();
    }

}