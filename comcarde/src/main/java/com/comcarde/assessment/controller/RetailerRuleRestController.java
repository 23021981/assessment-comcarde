package com.comcarde.assessment.controller;

import com.comcarde.assessment.exception.RuleNotFoundException;
import com.comcarde.assessment.models.AdviceDetails;
import com.comcarde.assessment.models.ProductDetails;
import com.comcarde.assessment.models.Rule;
import com.comcarde.assessment.models.db.RuleDbModel;
import com.comcarde.assessment.services.RuleBaseService;
import com.comcarde.assessment.enums.RuleNamespace;
import com.google.common.base.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *Author : Atul Kumar
 */
@RestController
@RequestMapping("/retailer")
public class RetailerRuleRestController {
    @Autowired
    private RuleBaseService ruleBaseService;

    @GetMapping(value = "/getAllRules/{ruleNamespace}")
    public List<Rule>  getRulesByNamespace(@PathVariable("ruleNamespace") String ruleNamespace) throws RuleNotFoundException {
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, ruleNamespace.toUpperCase()).or(RuleNamespace.DEFAULT);
        List<Rule> allRules = ruleBaseService.getAllRuleByNamespace(namespace.toString());
        return allRules;
    }

    @GetMapping(value = "/getAllRules")
    public List<Rule> getAllRules() throws RuleNotFoundException {
        List<Rule> allRules = ruleBaseService.getAllRules();
        return allRules;
    }

    @PostMapping(path= "/addRule", consumes = "application/json", produces = "application/json")
    public ResponseEntity addRule(@RequestBody RuleDbModel rule){
        ruleBaseService.saveRule(rule);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/checkRule")
    public AdviceDetails checkStockLevel(@RequestBody ProductDetails productDetails) throws RuleNotFoundException {
        AdviceDetails result = ruleBaseService.checkRule(productDetails);
        return result;
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
