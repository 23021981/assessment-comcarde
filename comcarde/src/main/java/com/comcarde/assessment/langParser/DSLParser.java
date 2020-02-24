package com.comcarde.assessment.langParser;

import com.comcarde.assessment.dslResolver.DSLKeywordResolver;
import com.comcarde.assessment.dslResolver.DSLResolver;
import com.comcarde.assessment.services.impl.DSLPatternServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *Author : Atul Kumar
 */
@Service
public class DSLParser {

    @Autowired
    private DSLKeywordResolver keywordResolver;
    @Autowired
    private DSLPatternServiceImpl dslPatternService;

    public String resolveDomainSpecificKeywords(String expression){
        Map<String, Object> dslKeywordToResolverValueMap = executeDSLResolver(expression);
        return replaceKeywordsWithValue(expression, dslKeywordToResolverValueMap);
    }

    private Map<String, Object> executeDSLResolver(String expression) {
        List<String> listOfDslKeyword = dslPatternService.getListOfDslKeywords(expression);
        Map<String, Object> dslKeywordToResolverValueMap = new HashMap<>();
        listOfDslKeyword.stream()
                .forEach(
                        dslKeyword -> {
                            String extractedDslKeyword = dslPatternService.extractKeyword(dslKeyword);
                            String keyResolver = dslPatternService.getKeywordResolver(extractedDslKeyword);
                            String keywordValue = dslPatternService.getKeywordValue(extractedDslKeyword);
                            DSLResolver resolver = keywordResolver.getResolver(keyResolver).get();
                            Object resolveValue = resolver.resolveValue(keywordValue);
                            dslKeywordToResolverValueMap.put(dslKeyword, resolveValue);
                        }
                );
        return dslKeywordToResolverValueMap;
    }

    private String replaceKeywordsWithValue(String expression, Map<String, Object> dslKeywordToResolverValueMap){
        List<String> keyList = dslKeywordToResolverValueMap.keySet().stream().collect(Collectors.toList());
        for (int index = 0; index < keyList.size(); index++){
            String key = keyList.get(index);
            String dslResolveValue = dslKeywordToResolverValueMap.get(key).toString();
            expression = expression.replace(key, dslResolveValue);
        }
        return expression;
    }
}
