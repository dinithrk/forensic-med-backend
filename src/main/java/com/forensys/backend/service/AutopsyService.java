package com.forensys.backend.service;

import com.forensys.backend.dto.DeceasedDto;
import com.forensys.backend.dto.PostMortemDto;
import com.forensys.backend.dto.AutopsyExamDto;

public interface AutopsyService {
    DeceasedDto registerDeceased(DeceasedDto deceasedDto);
    PostMortemDto createPostMortem(PostMortemDto postMortemDto);
    PostMortemDto finalizeAutopsyExam(Long pmSerialNo, AutopsyExamDto autopsyExamDto);
}
