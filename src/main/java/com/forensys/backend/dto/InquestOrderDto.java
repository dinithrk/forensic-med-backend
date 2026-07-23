package com.forensys.backend.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquestOrderDto {
    private Long inquestOrderId;
    private Long inquestNumber;
    private LocalDate dateOfInquest;
    private String inquirerFullName;
    private String inquirerDesignation;
    private String magistrate;
    private String policeStationArea;
    private String policeOfficerIncharge;
    private Boolean inquirerIntoSuddenDeaths;
    private String court;
    private String caseNo;
}
