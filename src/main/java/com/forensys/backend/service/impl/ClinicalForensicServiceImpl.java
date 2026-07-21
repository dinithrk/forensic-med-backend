package com.forensys.backend.service.impl;

import com.forensys.backend.dto.MlefRecordDto;
import com.forensys.backend.dto.MlrReportDto;
import com.forensys.backend.entity.IndividualInjury;
import com.forensys.backend.entity.MlefRecord;
import com.forensys.backend.entity.MlrReport;
import com.forensys.backend.entity.Referral;
import com.forensys.backend.mapper.ClinicalForensicMapper;
import com.forensys.backend.repository.IndividualInjuryRepository;
import com.forensys.backend.repository.MlefRecordRepository;
import com.forensys.backend.repository.MlrReportRepository;
import com.forensys.backend.repository.ReferralRepository;
import com.forensys.backend.service.ClinicalForensicService;
import com.forensys.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ClinicalForensicServiceImpl implements ClinicalForensicService {

    private static final String MLEF_NOT_FOUND_MSG = "MLEF Record not found";

    private final MlefRecordRepository mlefRecordRepository;
    private final IndividualInjuryRepository injuryRepository;
    private final ReferralRepository referralRepository;
    private final MlrReportRepository mlrReportRepository;
    private final com.forensys.backend.repository.PatientRepository patientRepository;
    private final ClinicalForensicMapper mapper;

    @Override
    @Transactional
    public MlefRecordDto createMlefRecord(MlefRecordDto dto) {
        MlefRecord mlefRecord = mapper.toEntity(dto);
        
        if (dto.getPatientId() != null) {
            com.forensys.backend.entity.Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Patient not found"));
            mlefRecord.setPatient(patient);
        }
        
        mlefRecord = mlefRecordRepository.save(mlefRecord);
        final MlefRecord savedRecord = mlefRecord;

        if (dto.getInjuries() != null && !dto.getInjuries().isEmpty()) {
            List<IndividualInjury> injuries = dto.getInjuries().stream()
                    .map(iDto -> {
                        IndividualInjury i = mapper.toEntity(iDto);
                        i.setMlefRecord(savedRecord);
                        return i;
                    })
                    .toList();
            injuryRepository.saveAll(injuries);
        }

        if (dto.getReferrals() != null && !dto.getReferrals().isEmpty()) {
            List<Referral> referrals = dto.getReferrals().stream()
                    .map(rDto -> {
                        Referral r = mapper.toEntity(rDto);
                        r.setMlefRecord(savedRecord);
                        return r;
                    })
                    .toList();
            referralRepository.saveAll(referrals);
        }

        return mapper.toDto(mlefRecordRepository.findById(savedRecord.getMlefId())
                .orElseThrow(() -> new ResourceNotFoundException(MLEF_NOT_FOUND_MSG)));
    }

    @Override
    @Transactional(readOnly = true)
    public MlefRecordDto getMlefRecordById(Long mlefId) {
        MlefRecord mlefRecordEntity = mlefRecordRepository.findById(mlefId)
                .orElseThrow(() -> new ResourceNotFoundException(MLEF_NOT_FOUND_MSG));
        return mapper.toDto(mlefRecordEntity);
    }
    @Override
    @Transactional(readOnly = true)
    public List<MlefRecordDto> getAllMlefRecords() {
        return mlefRecordRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public MlefRecordDto updateMlefRecord(Long mlefId, MlefRecordDto dto) {
        MlefRecord existingRecord = mlefRecordRepository.findById(mlefId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(MLEF_NOT_FOUND_MSG));
                
        mapper.updateEntityFromDto(dto, existingRecord);
        
        if (dto.getPatientId() != null) {
            com.forensys.backend.entity.Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Patient not found"));
            existingRecord.setPatient(patient);
        }
        
        injuryRepository.deleteByMlefRecord_MlefId(mlefId);
        if (dto.getInjuries() != null && !dto.getInjuries().isEmpty()) {
            List<IndividualInjury> injuries = dto.getInjuries().stream()
                    .map(iDto -> {
                        IndividualInjury i = mapper.toEntity(iDto);
                        i.setMlefRecord(existingRecord);
                        return i;
                    })
                    .toList();
            injuryRepository.saveAll(injuries);
        }
        
        referralRepository.deleteByMlefRecord_MlefId(mlefId);
        if (dto.getReferrals() != null && !dto.getReferrals().isEmpty()) {
            List<Referral> referrals = dto.getReferrals().stream()
                    .map(rDto -> {
                        Referral r = mapper.toEntity(rDto);
                        r.setMlefRecord(existingRecord);
                        return r;
                    })
                    .toList();
            referralRepository.saveAll(referrals);
        }
        
        MlefRecord saved = mlefRecordRepository.save(existingRecord);
        return mapper.toDto(saved);
    }
    @Override
    @Transactional
    public MlrReportDto generateMlrReport(Long mlefId, MlrReportDto dto) {
        MlefRecord mlefRecord = mlefRecordRepository.findById(mlefId)
                .orElseThrow(() -> new ResourceNotFoundException(MLEF_NOT_FOUND_MSG));
        
        MlrReport report = mapper.toEntity(dto);
        report.setMlefRecord(mlefRecord);
        report = mlrReportRepository.save(report);
        
        return mapper.toDto(report);
    }
}
