package com.comcarde.assessment.service;

import com.comcarde.assessment.exception.RuleNotFoundException;
import com.comcarde.assessment.models.Rule;
import com.comcarde.assessment.models.db.RuleDbModel;
import com.comcarde.assessment.repository.RulesRepository;
import com.comcarde.assessment.repository.StockCheckAuditRepository;
import com.comcarde.assessment.services.impl.RuleBaseServiceImpl;
import com.comcarde.assessment.enums.RuleNamespace;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class RuleBaseServiceTest {

    @Mock
    RulesRepository rulesRepository;

    @Mock
    StockCheckAuditRepository stockCheckAuditRepository;

    @InjectMocks
    RuleBaseServiceImpl ruleBaseService;

    @Test
    public void shouldGetAllRules() throws RuleNotFoundException {

        // Given
        Mockito.when(rulesRepository.findAll()).thenReturn(getListOfRules());

        //When
        List<Rule> actualRule = ruleBaseService.getAllRules();

        //Then
        assertNotNull(actualRule);
        assertThat(actualRule.size(), equalTo(2));

    }

    @Test(expected=RuleNotFoundException.class)
    public void shouldNotGetAnyRule() throws RuleNotFoundException {

        // Given
        Mockito.when(rulesRepository.findAll()).thenReturn(new ArrayList<RuleDbModel>());

        //When
        List<Rule> actualRule = ruleBaseService.getAllRules();

    }

    private List<RuleDbModel> getListOfRules(){
        RuleDbModel rule1 = new RuleDbModel(RuleNamespace.ORDER.name(), "1",
                "(input.productName.equals('A') || input.productName.equals('B') || " +
                        "input.productName.equals('C') || input.productName.equals('E')) " +
                        "&& input.stockLevel <= 4",
                "output.setSuggestionMessage('Minimum stock level should be 4. Please place order');" +
                        "output.setProductName(input.productName);",
                1,
                "A minimum stock level of 4.");

        RuleDbModel rule2 = new RuleDbModel (RuleNamespace.ORDER.name(),
                "2",
                "(input.productName.equals('D')) && input.stockLevel <= 8 && $(stock.target_done) " +
                        "== false",
                "output.setSuggestionMessage('Minimum stock level should be 8. Please place one-off " +
                        "order of 15');output.setProductName(input.productName);",
                2,
                "A minimum stock level of 8 and one-off order of 15");


        List<RuleDbModel> allRulesByNamespace = Lists.newArrayList(rule1, rule2);
        return allRulesByNamespace;
    }

}
