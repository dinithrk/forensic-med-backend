package com.forensys.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdentifierDto {
    private Long identifierId;
    private String nicNumber;
    private String fullName;
    private String residingAddress;
}
