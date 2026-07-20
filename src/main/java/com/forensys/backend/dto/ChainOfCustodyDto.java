package com.forensys.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChainOfCustodyDto {
    private Long custodyId;
    
    @NotBlank(message = "Delivered by name is required")
    private String deliveredByName;
    
    private String deliveredByNic;
    private String deliveredByOccupation;
    
    @NotNull(message = "Delivery date is required")
    private LocalDate deliveryDate;
    
    private LocalTime deliveryTime;
    private Boolean jmoSignatureStatus;
    
    @NotBlank(message = "Accepted by name is required")
    private String acceptedByName;
    
    @NotNull(message = "Accepted date is required")
    private LocalDate acceptedDate;
}
