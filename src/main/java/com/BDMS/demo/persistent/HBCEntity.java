package com.BDMS.demo.persistent;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="hbcentity")
public class HBCEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int h_id;

    @Column(name ="hbc_name")
    private String hbc_name;

    @Column(name ="h_area")
    private String h_area;

    @Column(name ="h_city")
    private String h_city;

    @Column(name ="h_division")
    private String h_division;

    @Column(name ="h_phone")
    private String h_phone;

    @Column(name ="organization_type")
    private String organization_type;
}

