package com.forensys.backend.service;

import com.forensys.backend.dto.MlefRecordDto;
import com.forensys.backend.dto.MlrReportDto;

public interface ClinicalForensicService {
    MlefRecordDto createMlefRecord(MlefRecordDto mlefRecordDto);
    MlefRecordDto getMlefRecordById(Long mlefId);
    MlrReportDto generateMlrReport(Long mlefId, MlrReportDto mlrReportDto);
}
