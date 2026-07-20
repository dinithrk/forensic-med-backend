package com.forensys.backend.repository;

import com.forensys.backend.entity.PostMortem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMortemRepository extends JpaRepository<PostMortem, Long> {
    java.util.List<PostMortem> findTop10ByDeceased_FullNameContainingIgnoreCase(String query);
}
