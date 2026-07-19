package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "post_mortem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostMortem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pmSerialNo;

    private LocalDateTime dateTimeOfPmExam;
    private String placeOfExamination;
    private String district;
    private Boolean underInvestigation;
    private Boolean specimensRetained;

    @ManyToMany
    @JoinTable(
            name = "pm_medical_officer",
            joinColumns = @JoinColumn(name = "pm_serial_no"),
            inverseJoinColumns = @JoinColumn(name = "officer_id")
    )
    private Set<MedicalOfficer> medicalOfficers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deceased_id")
    private Deceased deceased;

    @OneToOne(mappedBy = "postMortem", cascade = CascadeType.ALL, orphanRemoval = true)
    private AutopsyExam autopsyExam;

    @OneToMany(mappedBy = "postMortem", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<PreAutopsyInformation> preAutopsyInformation;
}
