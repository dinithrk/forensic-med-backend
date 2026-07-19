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
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Deceased not found"));
        return mapper.toDto(deceased);
    }

    @Override
    @Transactional
    public PostMortemDto createPostMortem(PostMortemDto dto) {
        PostMortem pm = mapper.toEntity(dto);
        
        if (dto.getDeceasedId() != null) {
            Deceased deceased = deceasedRepository.findById(dto.getDeceasedId())
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Deceased not found"));
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
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("PostMortem not found"));
        return mapper.toDto(pm);
    }

    @Override
    @Transactional
    public PostMortemDto finalizeAutopsyExam(Long pmSerialNo, AutopsyExamDto dto) {
        PostMortem pm = postMortemRepository.findById(pmSerialNo)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("PostMortem not found"));
        
        AutopsyExam exam = mapper.toEntity(dto);
        exam.setPostMortem(pm);
        
        autopsyExamRepository.save(exam);
        
        return mapper.toDto(pm);
    }
}
