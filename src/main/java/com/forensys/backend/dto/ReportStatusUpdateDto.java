package com.forensys.backend.dto;

import com.forensys.backend.entity.enums.ReportStatus;
import lombok.Data;

@Data
public class ReportStatusUpdateDto {
    private ReportStatus status;
    private String notes;
}
