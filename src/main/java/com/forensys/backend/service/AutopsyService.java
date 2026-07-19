package com.forensys.backend.service;

import com.forensys.backend.dto.DeceasedDto;
import com.forensys.backend.dto.PostMortemDto;
import com.forensys.backend.dto.AutopsyExamDto;

import java.util.List;

public interface AutopsyService {
    DeceasedDto registerDeceased(DeceasedDto deceasedDto);
    List<DeceasedDto> getAllDeceased();
    DeceasedDto getDeceasedById(Long id);
    DeceasedDto updateDeceased(Long id, DeceasedDto dto);
    
    PostMortemDto createPostMortem(PostMortemDto postMortemDto);
    List<PostMortemDto> getAllPostMortemsForDeceased(Long deceasedId);
    PostMortemDto getPostMortemById(Long pmSerialNo);
    PostMortemDto updatePostMortem(Long deceasedId, Long pmId, PostMortemDto dto);
    
    PostMortemDto finalizeAutopsyExam(Long pmSerialNo, AutopsyExamDto autopsyExamDto);
}
