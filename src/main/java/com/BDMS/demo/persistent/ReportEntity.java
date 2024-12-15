package com.BDMS.demo.persistent;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "reports")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer report_id;

    @Column(name = "last_blood_donate")
    public Date last_blood_donate;

    @Column(name ="hosid")
    public Integer hosid;

    @Column(name ="useriid")
    public String useriid;
}
