package com.forensys.backend.dto;

import com.forensys.backend.entity.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeceasedDto {
    private Long deceasedId;
    private String fullName;
    private Integer ageWhenDied;
    private Gender sex;
    private String lastAddress;
    private String placeOfDeath;
    private String hospitalName;
    private String bhtNo;
    private String wardNo;
    private LocalDate dateOfDeath;
    private LocalTime timeOfDeath;
    
    // Nested children
    private List<IdentifierDto> identifiers;
    private InquestOrderDto inquestOrder;
}
