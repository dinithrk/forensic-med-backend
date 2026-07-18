package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "inquest_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquestOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquestNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deceased_id")
    private Deceased deceased;

    private LocalDate dateOfInquest;

    // Inquirer Details
    private String inquirerFullName;
    private String inquirerDesignation;
    private String magistrate;

    // Police Station
    private String policeStationArea;
    private String policeOfficerIncharge;

    private Boolean inquirerIntoSuddenDeaths;
    private String court;
    private String caseNo;
}
