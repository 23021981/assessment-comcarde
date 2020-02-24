package com.comcarde.assessment.repository;

import com.comcarde.assessment.models.db.StockCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *Author : Atul Kumar
 */
@Repository
public interface StockCheckAuditRepository extends JpaRepository<StockCheck, Long> {

}
