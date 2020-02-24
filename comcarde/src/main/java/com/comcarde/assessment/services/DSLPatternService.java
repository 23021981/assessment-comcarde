package com.comcarde.assessment.services;

import java.util.List;
/**
 *Author : Atul Kumar
 */
public interface DSLPatternService {
    List<String> getListOfDslKeywords(String expression);

    String extractKeyword(String keyword);

    String getKeywordResolver(String dslKeyword);

    String getKeywordValue(String dslKeyword);
}
