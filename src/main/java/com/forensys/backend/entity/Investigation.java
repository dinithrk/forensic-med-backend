package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "investigation")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Investigation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long investigationId;

    private LocalDate investigationDate;
    private String findings;
    private String type;
    private String description;
    private String result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_serial_no")
    private PostMortem postMortem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "injury_id")
    private IndividualInjury individualInjury;
}
