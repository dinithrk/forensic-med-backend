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
    private final AutopsyMapper mapper;

    @Override
    @Transactional
    public DeceasedDto registerDeceased(DeceasedDto dto) {
        Deceased deceased = mapper.toEntity(dto);
        deceased = deceasedRepository.save(deceased);
        return mapper.toDto(deceased);
    }

    @Override
    @Transactional
    public PostMortemDto createPostMortem(PostMortemDto dto) {
        PostMortem pm = mapper.toEntity(dto);
        pm = postMortemRepository.save(pm);
        return mapper.toDto(pm);
    }

    @Override
    @Transactional
    public PostMortemDto finalizeAutopsyExam(Long pmSerialNo, AutopsyExamDto dto) {
        PostMortem pm = postMortemRepository.findById(pmSerialNo)
                .orElseThrow(() -> new RuntimeException("PostMortem not found"));
        
        AutopsyExam exam = mapper.toEntity(dto);
        exam.setPostMortem(pm);
        autopsyExamRepository.save(exam);
        
        return mapper.toDto(pm);
    }
}
