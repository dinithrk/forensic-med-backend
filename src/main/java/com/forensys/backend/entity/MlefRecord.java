package com.forensys.backend.entity;

import com.forensys.backend.entity.enums.SubstanceInfluence;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "mlef_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MlefRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mlefId;

    // Police Form
    private String policeRefNo;
    private LocalDate policeDateOfIssue;
    private String reasonForReferral;
    private LocalDateTime dateTimeExamined;
    private String placeExamined;

    // Nature of bodily harm
    private Boolean injuryAbrasion;
    private Boolean injuryContusion;
    private Boolean injuryLaceration;
    private Boolean injuryStab;
    private Boolean injuryCut;
    private Boolean injuryFracture;
    private Boolean injuryFirearm;
    private Boolean injuryBurns;
    private Boolean injuryBite;
    private Boolean injuryDislocation;
    private Boolean injuryExplosive;
    private Boolean injuryNone;
    private Boolean internalInjuries;
    private String othersNatureOfHarm;

    // Alcohol and Drug
    private String breathingSmellIntensity;

    @Enumerated(EnumType.STRING)
    private SubstanceInfluence alcoholInfluence;

    private Boolean drugConsumed;

    @Enumerated(EnumType.STRING)
    private SubstanceInfluence drugInfluence;

    // Sexual Assault Exam
    private String sexualAssaultBriefHistory;
    private Boolean signsVaginalHymenPenetration;
    private Boolean signsAnalPenetration;
    private Boolean signsInterLabialPenetration;
    private String otherOpinionsRecommendations;

    // Hospital Admission
    private String hospitalName;
    private String hospitalWard;
    private String hospitalBhtNo;
    private LocalDate dateAdmitted;
    private LocalTime timeAdmitted;
    private LocalDate dateDischarged;

    private String remarks;
    private String shortHistoryGivenByPatient;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brought_by_officer_id")
    private PoliceOfficer broughtByOfficer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_officer_id")
    private MedicalOfficer assignedMedicalOfficer;

    @OneToMany(mappedBy = "mlefRecord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IndividualInjury> injuries;

    @OneToMany(mappedBy = "mlefRecord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Referral> referrals;
}
