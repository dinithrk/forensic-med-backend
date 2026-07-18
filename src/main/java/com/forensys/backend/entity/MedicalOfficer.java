package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medical_officer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalOfficer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "officer_id")
    private Long officerId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "qualifications")
    private String qualifications;

    @Column(name = "slmc_reg_no", unique = true)
    private String slmcRegNo;

    @Column(name = "designation")
    private String designation;
}
