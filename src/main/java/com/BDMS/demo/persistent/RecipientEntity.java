package com.BDMS.demo.persistent;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

;

@Data
@Entity
@Table(name="Recipient")
public class RecipientEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer r_id;

    @Column(name="medical_purpose")
    public String medical_purpose;

    @Column(name="registration_date")
    public String registration_date;

    @Column(name="volume_needed")
    public Integer volume_needed;

    @Column(name="blood_g_needed")
    public String blood_g_needed;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "hbc_id", nullable = false)
    private HBCEntity hbc;

}
