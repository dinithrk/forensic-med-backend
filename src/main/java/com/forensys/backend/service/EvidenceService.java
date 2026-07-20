package com.forensys.backend.service;

import com.forensys.backend.dto.ChainOfCustodyDto;
import com.forensys.backend.dto.ForensicSampleDto;

public interface EvidenceService {
    java.util.List<ForensicSampleDto> getAllSamples();
    ForensicSampleDto getSampleById(Long sampleId);
    ForensicSampleDto registerSample(ForensicSampleDto sampleDto);
    ForensicSampleDto updateSample(Long sampleId, ForensicSampleDto sampleDto);
    ChainOfCustodyDto logCustodyTransfer(Long sampleId, ChainOfCustodyDto custodyDto);
}
