package com.comcarde.assessment.langParser;

import com.comcarde.assessment.services.impl.DSLPatternServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DSLPatternTest {

    private static final String expression1 = "input.productName.equals('D')) && input.stockLevel <= 8 && $(stock.target_done)== false";
    private static final String expression2 = "input.productName.equals('D')) && $(input.stockLevel) <= 8 && $(stock.target_done)== false";

    private DSLPatternServiceImpl dslPatternServiceImpl;

    @Before
    public void setUp (){
        dslPatternServiceImpl = new DSLPatternServiceImpl();
    }

    @Test
    public void verifyPatternInExpression(){
        String keyword = dslPatternServiceImpl.getListOfDslKeywords(expression1).get(0);
        assertThat(keyword).isEqualTo("$(stock.target_done)");
    }
    @Test
    public void verifyNumberOfPatternFoundInExpression(){
        int numberOfPatters = dslPatternServiceImpl.getListOfDslKeywords(expression2).size();
        assertThat(numberOfPatters).isEqualTo(2);
    }

    @Test
    public void verifyExtractValue(){
        String keyword = dslPatternServiceImpl.getListOfDslKeywords(expression1).get(0);
        assertThat(dslPatternServiceImpl.extractKeyword(keyword)).isEqualTo("stock.target_done");
    }

    @Test
    public void verifyKeywordResolverValue(){
        String keyword = dslPatternServiceImpl.getListOfDslKeywords(expression1).get(0);
        assertThat(dslPatternServiceImpl.getKeywordResolver(dslPatternServiceImpl.extractKeyword(keyword))).isEqualTo("stock");
    }

    @Test
    public void verifyKeywordValue(){
        String keyword = dslPatternServiceImpl.getListOfDslKeywords(expression1).get(0);
        assertThat(dslPatternServiceImpl.getKeywordValue(dslPatternServiceImpl.extractKeyword(keyword))).isEqualTo("target_done");
    }
    
}
