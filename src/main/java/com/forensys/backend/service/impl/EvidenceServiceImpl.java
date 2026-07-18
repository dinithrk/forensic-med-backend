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

    private final ForensicSampleRepository forensicSampleRepository;
    private final ChainOfCustodyRepository chainOfCustodyRepository;
    private final EvidenceMapper mapper;

    @Override
    @Transactional
    public ForensicSampleDto registerSample(ForensicSampleDto dto) {
        ForensicSample sample = mapper.toEntity(dto);
        sample = forensicSampleRepository.save(sample);
        return mapper.toDto(sample);
    }

    @Override
    @Transactional
    public ChainOfCustodyDto logCustodyTransfer(Long sampleId, ChainOfCustodyDto dto) {
        ForensicSample sample = forensicSampleRepository.findById(sampleId)
                .orElseThrow(() -> new RuntimeException("Sample not found"));
        
        ChainOfCustody custody = mapper.toEntity(dto);
        custody.setForensicSample(sample);
        // Ensure this is a new log entry, enforcing append-only pattern
        custody.setCustodyId(null); // Force insert instead of update
        
        custody = chainOfCustodyRepository.save(custody);
        return mapper.toDto(custody);
    }
}
