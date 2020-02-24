package com.comcarde.assessment.ruleEngine;

import com.comcarde.assessment.enums.RuleNamespace;
import com.comcarde.assessment.models.AdviceDetails;
import com.comcarde.assessment.models.ProductDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *Author : Atul Kumar
 */
@Slf4j
@Service
public class StockInferenceEngine extends InferenceEngine<ProductDetails, AdviceDetails> {

    @Override
    protected RuleNamespace getRuleNamespace() {
        return RuleNamespace.ORDER;
    }

    @Override
    protected AdviceDetails initializeOutputResult() {
        return new AdviceDetails();
    }
}
