package com.forensys.backend.entity;

import com.forensys.backend.entity.enums.CategoryOfHurt;
import com.forensys.backend.entity.enums.NatureOfBodilyHarm;
import com.forensys.backend.entity.enums.WeaponCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "individual_injury")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualInjury {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long injuryId;

    private String autoSeqNumber;
    private String injuryClass;

    @Enumerated(EnumType.STRING)
    private NatureOfBodilyHarm natureOfBodilyHarm;

    private String detailedDescription;
    private String diagramTagLabel;

    @Enumerated(EnumType.STRING)
    private WeaponCategory weaponCategory;

    private String specificWeaponName;
    private String remarks;

    @Enumerated(EnumType.STRING)
    private CategoryOfHurt categoryOfHurt;

    private String penalCodeSection;
    private String explanatoryRemarks;
    private String injuryToCourseOfDeath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mlef_id", nullable = false)
    private MlefRecord mlefRecord;
}
