package com.forensys.backend.service;

import com.forensys.backend.dto.MlefRecordDto;
import com.forensys.backend.dto.MlrReportDto;

import java.util.List;

public interface ClinicalForensicService {
    MlefRecordDto createMlefRecord(MlefRecordDto mlefRecordDto);
    MlefRecordDto getMlefRecordById(Long mlefId);
    List<MlefRecordDto> getAllMlefRecords();
    MlefRecordDto updateMlefRecord(Long mlefId, MlefRecordDto mlefRecordDto);
    MlrReportDto generateMlrReport(Long mlefId, MlrReportDto mlrReportDto);
}
