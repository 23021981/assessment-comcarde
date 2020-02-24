package com.comcarde.assessment.ruleEngine;

import com.comcarde.assessment.exception.RuleNotFoundException;
import com.comcarde.assessment.services.impl.RuleBaseServiceImpl;
import com.comcarde.assessment.models.Rule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *Author : Atul Kumar
 */
@Service
public class RuleEngine {

    @Autowired
    private RuleBaseServiceImpl ruleBaseServiceImpl;

    public Object run(InferenceEngine inferenceEngine, Object inputData) throws RuleNotFoundException {
        String ruleNamespace = inferenceEngine.getRuleNamespace().toString();
        //TODO: Here for each call, we are fetching all rules from db. It should be cache.
        List<Rule> allRulesByNamespace = ruleBaseServiceImpl.getAllRuleByNamespace(ruleNamespace);
        Object result = inferenceEngine.run(allRulesByNamespace, inputData);
        return result;
    }

}
