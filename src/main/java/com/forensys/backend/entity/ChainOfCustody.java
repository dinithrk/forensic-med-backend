package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "chain_of_custody")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChainOfCustody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long custodyId;

    private String deliveredByName;
    private String deliveredByNic;
    private String deliveredByOccupation;
    private LocalDate deliveryDate;
    private LocalTime deliveryTime;
    private Boolean jmoSignatureStatus;
    private String acceptedByName;
    private LocalDate acceptedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_id", nullable = false)
    private ForensicSample forensicSample;
}
