package com.comcarde.assessment.services;

import com.comcarde.assessment.exception.RuleNotFoundException;
import com.comcarde.assessment.models.AdviceDetails;
import com.comcarde.assessment.models.ProductDetails;
import com.comcarde.assessment.models.Rule;
import com.comcarde.assessment.models.db.RuleDbModel;

import java.util.List;

/**
 *Author : Atul Kumar
 */
public interface RuleBaseService {
    List<Rule> getAllRules() throws RuleNotFoundException;

    List<Rule> getAllRuleByNamespace(String ruleNamespace) throws RuleNotFoundException;

    void saveRule(RuleDbModel ruleDbModel);

    AdviceDetails checkRule(ProductDetails productDetails) throws RuleNotFoundException;
}
