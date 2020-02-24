package com.comcarde.assessment.dslResolver;
/**
 *Author : Atul Kumar
 */
public interface DSLResolver {
    String getResolverKeyword();
    Object resolveValue(String keyword);
}
