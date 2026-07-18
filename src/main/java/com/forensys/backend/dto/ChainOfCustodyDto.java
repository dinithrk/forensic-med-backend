package com.forensys.backend.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChainOfCustodyDto {
    private Long custodyId;
    private String deliveredByName;
    private String deliveredByNic;
    private String deliveredByOccupation;
    private LocalDate deliveryDate;
    private LocalTime deliveryTime;
    private Boolean jmoSignatureStatus;
    private String acceptedByName;
    private LocalDate acceptedDate;
}
