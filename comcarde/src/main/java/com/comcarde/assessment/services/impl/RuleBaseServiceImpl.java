package com.comcarde.assessment.services.impl;

import com.comcarde.assessment.exception.RuleNotFoundException;
import com.comcarde.assessment.models.AdviceDetails;
import com.comcarde.assessment.ruleEngine.StockInferenceEngine;
import com.comcarde.assessment.models.ProductDetails;
import com.comcarde.assessment.models.db.StockCheck;
import com.comcarde.assessment.repository.StockCheckAuditRepository;
import com.comcarde.assessment.ruleEngine.RuleEngine;
import com.comcarde.assessment.services.RuleBaseService;
import com.comcarde.assessment.enums.RuleNamespace;
import com.google.common.base.Enums;
import com.comcarde.assessment.models.db.RuleDbModel;
import com.comcarde.assessment.repository.RulesRepository;
import com.comcarde.assessment.models.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 *Author : Atul Kumar
 */

@Service
public class RuleBaseServiceImpl implements RuleBaseService {
    @Autowired
    private RulesRepository rulesRepository;
    @Autowired
    private RuleEngine ruleEngine;
    @Autowired
    private StockInferenceEngine stockInferenceEngine;
    @Autowired
    private StockCheckAuditRepository stockCheckAuditRepository;

    @Override
    public List<Rule> getAllRules() throws RuleNotFoundException {
        List<Rule> rules = rulesRepository.findAll().stream()
                .map(
                        ruleDbModel -> mapFromDbModel(ruleDbModel)
                )
                .collect(Collectors.toList());
        if(rules.size() < 1){
            throw new RuleNotFoundException("There is no rule available in table");
        }
        return rules;
    }

    @Override
    public List<Rule> getAllRuleByNamespace(String ruleNamespace) throws RuleNotFoundException {
        List<Rule> rules =  rulesRepository.findByRuleNamespace(ruleNamespace).stream()
                .map(
                        ruleDbModel -> mapFromDbModel(ruleDbModel)
                )
                .collect(Collectors.toList());
        if(rules.size() < 1){
            throw new RuleNotFoundException("There is no rule available in table");
        }
        return rules;
    }

    @Override
    public void saveRule(RuleDbModel ruleDbModel) {
        rulesRepository.save(ruleDbModel);
    }

    @Override
    public AdviceDetails checkRule(ProductDetails productDetails) throws RuleNotFoundException {
        AdviceDetails result = (AdviceDetails) ruleEngine.run(stockInferenceEngine, productDetails);
        Calendar calendar = Calendar.getInstance();
        if(result == null){
            result = new AdviceDetails();
            result.setProductName(productDetails.getProductName());
            result.setSuggestionMessage("No Advice for this Product");
            stockCheckAuditRepository.save(new StockCheck(productDetails.getProductName(),
                    true,false,
                    new java.sql.Timestamp(calendar.getTime().getTime()), result.getSuggestionMessage()));
        } else {
            stockCheckAuditRepository.save(new StockCheck(result.getProductName(),
                    true,true,
                    new java.sql.Timestamp(calendar.getTime().getTime()), result.getSuggestionMessage()));
        }

        return result;
    }


    private Rule mapFromDbModel(RuleDbModel ruleDbModel){
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, ruleDbModel.getRuleNamespace().toUpperCase())
                .or(RuleNamespace.DEFAULT);
        return new Rule(namespace,ruleDbModel.getRuleId(),ruleDbModel.getCondition(),
                ruleDbModel.getAction(),
                ruleDbModel.getPriority(),
                ruleDbModel.getDescription()
                );

    }
}
