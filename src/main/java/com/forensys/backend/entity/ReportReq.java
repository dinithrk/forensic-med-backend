package com.forensys.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "report_req")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReportReq extends CourtRequest {
    private LocalDate requiredDate;
    private LocalDate reportSentDate;
    private String certificateOfReceipt;
}
