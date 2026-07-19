package com.forensys.backend.service.impl;

import com.forensys.backend.dto.AutopsyExamDto;
import com.forensys.backend.dto.DeceasedDto;
import com.forensys.backend.dto.PostMortemDto;
import com.forensys.backend.entity.AutopsyExam;
import com.forensys.backend.entity.Deceased;
import com.forensys.backend.entity.PostMortem;
import com.forensys.backend.mapper.AutopsyMapper;
import com.forensys.backend.repository.AutopsyExamRepository;
import com.forensys.backend.repository.DeceasedRepository;
import com.forensys.backend.repository.PostMortemRepository;
import com.forensys.backend.service.AutopsyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutopsyServiceImpl implements AutopsyService {

    private final DeceasedRepository deceasedRepository;
    private final PostMortemRepository postMortemRepository;
    private final AutopsyExamRepository autopsyExamRepository;
    private final com.forensys.backend.repository.MedicalOfficerRepository medicalOfficerRepository;
    private final AutopsyMapper mapper;

    private static final String DECEASED_NOT_FOUND = "Deceased not found";
    private static final String POSTMORTEM_NOT_FOUND = "PostMortem not found";

    @Override
    @Transactional
    public DeceasedDto registerDeceased(DeceasedDto dto) {
        Deceased deceased = mapper.toEntity(dto);
        
        if (deceased.getInquestOrder() != null) {
            deceased.getInquestOrder().setDeceased(deceased);
        }
        
        if (deceased.getIdentifiers() != null) {
            final Deceased finalDeceased = deceased;
            deceased.getIdentifiers().forEach(i -> i.setDeceased(finalDeceased));
        }
        
        Deceased savedDeceased = deceasedRepository.save(deceased);
        return mapper.toDto(savedDeceased);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<DeceasedDto> getAllDeceased() {
        return deceasedRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DeceasedDto getDeceasedById(Long id) {
        Deceased deceased = deceasedRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(DECEASED_NOT_FOUND));
        return mapper.toDto(deceased);
    }
    
    @Override
    @Transactional
    public DeceasedDto updateDeceased(Long id, DeceasedDto dto) {
        Deceased existing = deceasedRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(DECEASED_NOT_FOUND));
        
        mapper.updateDeceasedFromDto(dto, existing);
        
        // Handle inquest order
        if (dto.getInquestOrder() != null) {
            com.forensys.backend.entity.InquestOrder io = existing.getInquestOrder();
            if (io == null) {
                io = new com.forensys.backend.entity.InquestOrder();
                io.setDeceased(existing);
                existing.setInquestOrder(io);
            }
            io.setInquestNumber(dto.getInquestOrder().getInquestNumber());
            io.setDateOfInquest(dto.getInquestOrder().getDateOfInquest());
            io.setInquirerFullName(dto.getInquestOrder().getInquirerFullName());
            io.setInquirerDesignation(dto.getInquestOrder().getInquirerDesignation());
            io.setMagistrate(dto.getInquestOrder().getMagistrate());
            io.setPoliceStationArea(dto.getInquestOrder().getPoliceStationArea());
            io.setPoliceOfficerIncharge(dto.getInquestOrder().getPoliceOfficerIncharge());
            io.setInquirerIntoSuddenDeaths(dto.getInquestOrder().getInquirerIntoSuddenDeaths());
            io.setCourt(dto.getInquestOrder().getCourt());
            io.setCaseNo(dto.getInquestOrder().getCaseNo());
        } else if (existing.getInquestOrder() != null) {
            existing.setInquestOrder(null);
        }
        
        // Handle identifiers
        if (dto.getIdentifiers() != null) {
            if (existing.getIdentifiers() != null) {
                existing.getIdentifiers().clear();
            } else {
                existing.setIdentifiers(new java.util.ArrayList<>());
            }
            for (com.forensys.backend.dto.IdentifierDto idto : dto.getIdentifiers()) {
                com.forensys.backend.entity.Identifier identifier = new com.forensys.backend.entity.Identifier();
                identifier.setFullName(idto.getFullName());
                identifier.setNicNumber(idto.getNicNumber());
                identifier.setResidingAddress(idto.getResidingAddress());
                identifier.setDeceased(existing);
                existing.getIdentifiers().add(identifier);
            }
        } else if (existing.getIdentifiers() != null) {
            existing.getIdentifiers().clear();
        }
        
        Deceased updated = deceasedRepository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public PostMortemDto createPostMortem(PostMortemDto dto) {
        PostMortem pm = mapper.toEntity(dto);
        
        if (dto.getDeceasedId() != null) {
            Deceased deceased = deceasedRepository.findById(dto.getDeceasedId())
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(DECEASED_NOT_FOUND));
            pm.setDeceased(deceased);
        }
        
        if (dto.getMedicalOfficerIds() != null && !dto.getMedicalOfficerIds().isEmpty()) {
            java.util.Set<com.forensys.backend.entity.MedicalOfficer> officers = new java.util.HashSet<>(
                    medicalOfficerRepository.findAllById(dto.getMedicalOfficerIds())
            );
            pm.setMedicalOfficers(officers);
        }
        
        if (pm.getPreAutopsyInformation() != null) {
            final PostMortem finalPm = pm;
            pm.getPreAutopsyInformation().forEach(info -> info.setPostMortem(finalPm));
        }
        
        pm = postMortemRepository.save(pm);
        return mapper.toDto(pm);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<PostMortemDto> getAllPostMortemsForDeceased(Long deceasedId) {
        // Find all postmortems where the deceased ID matches
        // (Assuming the relationship is set correctly, this requires a custom query if not fetching from Deceased)
        // Since we mapped it, we can fetch from repository
        return postMortemRepository.findAll().stream()
                .filter(pm -> pm.getDeceased() != null && pm.getDeceased().getDeceasedId().equals(deceasedId))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PostMortemDto getPostMortemById(Long pmSerialNo) {
        PostMortem pm = postMortemRepository.findById(pmSerialNo)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(POSTMORTEM_NOT_FOUND));
        return mapper.toDto(pm);
    }

    @Override
    @Transactional
    public PostMortemDto updatePostMortem(Long deceasedId, Long pmId, PostMortemDto dto) {
        PostMortem existing = postMortemRepository.findById(pmId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(POSTMORTEM_NOT_FOUND));
        
        if (existing.getDeceased() == null || !existing.getDeceased().getDeceasedId().equals(deceasedId)) {
            throw new IllegalArgumentException("Postmortem does not belong to the specified deceased ID");
        }
        
        mapper.updatePostMortemFromDto(dto, existing);
        
        if (dto.getMedicalOfficerIds() != null) {
            java.util.Set<com.forensys.backend.entity.MedicalOfficer> officers = new java.util.HashSet<>(
                    medicalOfficerRepository.findAllById(dto.getMedicalOfficerIds())
            );
            existing.setMedicalOfficers(officers);
        }
        
        if (dto.getPreAutopsyInformation() != null) {
            if (existing.getPreAutopsyInformation() != null) {
                existing.getPreAutopsyInformation().clear();
            } else {
                existing.setPreAutopsyInformation(new java.util.ArrayList<>());
            }
            for (com.forensys.backend.dto.PreAutopsyInformationDto infoDto : dto.getPreAutopsyInformation()) {
                com.forensys.backend.entity.PreAutopsyInformation info = new com.forensys.backend.entity.PreAutopsyInformation();
                info.setInformationCategory(infoDto.getInformationCategory());
                info.setRecordDetails(infoDto.getRecordDetails());
                info.setPostMortem(existing);
                existing.getPreAutopsyInformation().add(info);
            }
        }
        
        PostMortem updated = postMortemRepository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public PostMortemDto finalizeAutopsyExam(Long pmSerialNo, AutopsyExamDto dto) {
        PostMortem pm = postMortemRepository.findById(pmSerialNo)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(POSTMORTEM_NOT_FOUND));
        
        AutopsyExam exam = mapper.toEntity(dto);
        exam.setPostMortem(pm);
        
        autopsyExamRepository.save(exam);
        
        return mapper.toDto(pm);
    }
}
