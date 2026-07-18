package com.forensys.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferralDto {
    private Long refId;
    private String referredToConsultant;
    private String specialty;
    private String referralReason;
    private Boolean reportReceivedBack;
}
