package com.BDMS.demo.persistent;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import org.springframework.context.annotation.Primary;

import java.util.Date;

@Data
@Entity
@Table(name = "donation_entity")
public class DonationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public  Integer  don_id ;

    @Column(name= "don_date")
    public Date don_date;

    @Column(name = "recid")
    public Integer recid;

    @Column(name = "hos_id")
    public Integer hos_id;

    @Column(name = "use_id")
    public Integer use_id;
}
