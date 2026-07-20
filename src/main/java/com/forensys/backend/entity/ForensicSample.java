package com.forensys.backend.entity;

import com.forensys.backend.entity.enums.SpecimenType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

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
    
    private LocalDate collectionDate;
    private String collectedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mlef_id")
    private MlefRecord clinicalCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autopsy_id")
    private AutopsyExam autopsyExam;

    @OneToMany(mappedBy = "forensicSample", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChainOfCustody> chainOfCustody;
}
