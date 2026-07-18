package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "police_officer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoliceOfficer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "officer_id")
    private Long officerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rank")
    private String rank;

    @Column(name = "reg_no")
    private String regNo;

    @Column(name = "police_station")
    private String policeStation;
}
