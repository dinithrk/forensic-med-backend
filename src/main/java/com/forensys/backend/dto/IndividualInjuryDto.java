package com.forensys.backend.dto;

import com.forensys.backend.entity.enums.CategoryOfHurt;
import com.forensys.backend.entity.enums.NatureOfBodilyHarm;
import com.forensys.backend.entity.enums.WeaponCategory;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualInjuryDto {
    private Long injuryId;
    private String autoSeqNumber;
    private String injuryClass;
    private NatureOfBodilyHarm natureOfBodilyHarm;
    private String detailedDescription;
    private String diagramTagLabel;
    private WeaponCategory weaponCategory;
    private String specificWeaponName;
    private String remarks;
    private CategoryOfHurt categoryOfHurt;
    private String penalCodeSection;
    private String explanatoryRemarks;
    private String injuryToCourseOfDeath;
}
