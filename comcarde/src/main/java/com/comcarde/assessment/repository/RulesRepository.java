package com.comcarde.assessment.repository;

import com.comcarde.assessment.models.db.RuleDbModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 *Author : Atul Kumar
 */
@Repository
public interface RulesRepository extends JpaRepository<RuleDbModel, Long> {
    List<RuleDbModel> findByRuleNamespace(String ruleNamespace);
    List<RuleDbModel> findAll();
}
