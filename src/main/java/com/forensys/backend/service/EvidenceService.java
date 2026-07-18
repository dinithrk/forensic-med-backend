package com.forensys.backend.service;

import com.forensys.backend.dto.ChainOfCustodyDto;
import com.forensys.backend.dto.ForensicSampleDto;

public interface EvidenceService {
    ForensicSampleDto registerSample(ForensicSampleDto sampleDto);
    ChainOfCustodyDto logCustodyTransfer(Long sampleId, ChainOfCustodyDto custodyDto);
}
