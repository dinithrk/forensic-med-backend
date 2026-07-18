package com.forensys.backend.repository;

import com.forensys.backend.entity.InquestOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquestOrderRepository extends JpaRepository<InquestOrder, Long> {
}
