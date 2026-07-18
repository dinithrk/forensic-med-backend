package com.forensys.backend.repository;

import com.forensys.backend.entity.AutopsyExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutopsyExamRepository extends JpaRepository<AutopsyExam, Long> {
}
