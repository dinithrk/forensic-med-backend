package com.forensys.backend.repository;

import com.forensys.backend.entity.Deceased;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeceasedRepository extends JpaRepository<Deceased, Long> {
}
