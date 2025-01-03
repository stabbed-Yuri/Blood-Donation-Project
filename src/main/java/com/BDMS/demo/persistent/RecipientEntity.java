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

    @Setter
    @Getter
    @Column(name = "total_matching_donor_notified")
    private Integer totalMatchingDonorNotified;

    @Getter
    @Column(name = "accepted_count")
    private Integer acceptedCount;

public Integer getAcceptedCount() {
        return acceptedCount != null ? acceptedCount : 0;
    }
    public String getHbcDivision() {
        return this.hbc.getH_division();
    }
    @Column(name = "rejected_count")
    private Integer rejectedCount;
    public Integer getRejectedCount() {
        return rejectedCount != null ? rejectedCount : 0;
    }

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

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            name = "recipient_completed_donors",
            joinColumns = @JoinColumn(name = "recipient_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> completedDonors;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipient_responded_users",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> respondedUsers ;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipient_rejected_users",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> rejectedUsers;
}