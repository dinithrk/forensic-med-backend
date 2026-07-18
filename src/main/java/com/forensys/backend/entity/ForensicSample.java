package com.forensys.backend.entity;

import com.forensys.backend.entity.enums.SpecimenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "forensic_sample")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForensicSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sampleId;

    @Enumerated(EnumType.STRING)
    private SpecimenType specimenType;

    private String organSource;
    private Integer numberOfTissues;
    private String productionNumber;
    private String referredInstitution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_id")
    private CaseRegister caseRegister;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autopsy_id")
    private AutopsyExam autopsyExam;
}
