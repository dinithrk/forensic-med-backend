package com.forensys.backend.service.impl;

import com.forensys.backend.dto.ChainOfCustodyDto;
import com.forensys.backend.dto.ForensicSampleDto;
import com.forensys.backend.entity.ChainOfCustody;
import com.forensys.backend.entity.ForensicSample;
import com.forensys.backend.mapper.EvidenceMapper;
import com.forensys.backend.repository.ChainOfCustodyRepository;
import com.forensys.backend.repository.ForensicSampleRepository;
import com.forensys.backend.service.EvidenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EvidenceServiceImpl implements EvidenceService {

    private static final String SAMPLE_NOT_FOUND_MSG = "Sample not found";

    private final ForensicSampleRepository forensicSampleRepository;
    private final ChainOfCustodyRepository chainOfCustodyRepository;
    private final com.forensys.backend.repository.MlefRecordRepository mlefRecordRepository;
    private final com.forensys.backend.repository.AutopsyExamRepository autopsyExamRepository;
    private final EvidenceMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public java.util.List<ForensicSampleDto> getAllSamples() {
        return forensicSampleRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ForensicSampleDto getSampleById(Long sampleId) {
        ForensicSample sample = forensicSampleRepository.findById(sampleId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(SAMPLE_NOT_FOUND_MSG));
        return mapper.toDto(sample);
    }

    @Override
    @Transactional
    public ForensicSampleDto registerSample(ForensicSampleDto dto) {
        ForensicSample sample = mapper.toEntity(dto);
        
        if (dto.getCaseId() != null) {
            com.forensys.backend.entity.MlefRecord clinicalCase = mlefRecordRepository.findById(dto.getCaseId())
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Clinical case not found"));
            sample.setClinicalCase(clinicalCase);
        } else if (dto.getPmSerialNo() != null) {
            com.forensys.backend.entity.AutopsyExam autopsy = autopsyExamRepository.findByPostMortem_PmSerialNo(dto.getPmSerialNo())
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Postmortem Autopsy Exam not found"));
            sample.setAutopsyExam(autopsy);
        }
        
        sample = forensicSampleRepository.save(sample);
        return mapper.toDto(sample);
    }

    @Override
    @Transactional
    public ForensicSampleDto updateSample(Long sampleId, ForensicSampleDto dto) {
        ForensicSample existing = forensicSampleRepository.findById(sampleId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(SAMPLE_NOT_FOUND_MSG));
        
        mapper.updateForensicSampleFromDto(dto, existing);
        
        // Re-link if provided
        if (dto.getCaseId() != null) {
            com.forensys.backend.entity.MlefRecord clinicalCase = mlefRecordRepository.findById(dto.getCaseId())
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Clinical case not found"));
            existing.setClinicalCase(clinicalCase);
        } else if (dto.getPmSerialNo() != null) {
            com.forensys.backend.entity.AutopsyExam autopsy = autopsyExamRepository.findByPostMortem_PmSerialNo(dto.getPmSerialNo())
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Postmortem Autopsy Exam not found"));
            existing.setAutopsyExam(autopsy);
        }
        
        existing = forensicSampleRepository.save(existing);
        return mapper.toDto(existing);
    }

    @Override
    @Transactional
    public ChainOfCustodyDto logCustodyTransfer(Long sampleId, ChainOfCustodyDto dto) {
        ForensicSample sample = forensicSampleRepository.findById(sampleId)
                .orElseThrow(() -> new RuntimeException(SAMPLE_NOT_FOUND_MSG));
        
        ChainOfCustody custody = mapper.toEntity(dto);
        custody.setForensicSample(sample);
        // Ensure this is a new log entry, enforcing append-only pattern
        custody.setCustodyId(null); // Force insert instead of update
        
        custody = chainOfCustodyRepository.save(custody);
        return mapper.toDto(custody);
    }
}
