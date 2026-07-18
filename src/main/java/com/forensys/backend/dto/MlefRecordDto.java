package com.forensys.backend.dto;

import com.forensys.backend.entity.enums.SubstanceInfluence;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MlefRecordDto {
    private Long mlefId;
    private String policeRefNo;
    private LocalDate policeDateOfIssue;
    private String reasonForReferral;
    private LocalDateTime dateTimeExamined;
    private String placeExamined;

    // Nature of bodily harm booleans (summarized for DTO)
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
    private SubstanceInfluence alcoholInfluence;
    private Boolean drugConsumed;
    private SubstanceInfluence drugInfluence;

    // Sexual Assault
    private String sexualAssaultBriefHistory;
    private Boolean signsVaginalHymenPenetration;
    private Boolean signsAnalPenetration;
    private Boolean signsInterLabialPenetration;
    private String otherOpinionsRecommendations;

    // Hospital
    private String hospitalName;
    private String hospitalWard;
    private String hospitalBhtNo;
    private LocalDate dateAdmitted;
    private LocalTime timeAdmitted;
    private LocalDate dateDischarged;

    private String remarks;
    private String shortHistoryGivenByPatient;

    // Patient ID Reference
    private Long patientId;
    private Long broughtByOfficerId;
    private Long assignedMedicalOfficerId;

    // Nested children
    private List<IndividualInjuryDto> injuries;
    private List<ReferralDto> referrals;
}
