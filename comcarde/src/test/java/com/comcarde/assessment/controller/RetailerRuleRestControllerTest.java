package com.comcarde.assessment.controller;

import com.comcarde.assessment.models.AdviceDetails;
import com.comcarde.assessment.models.ProductDetails;
import com.comcarde.assessment.models.Rule;
import com.comcarde.assessment.models.db.RuleDbModel;
import com.comcarde.assessment.enums.RuleNamespace;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RetailerRuleRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void verifyGetAllRules() throws Exception {
        mockMvc.perform(get("/retailer/getAllRules")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
                );
    }

    @Test
    public void verifyGetRulesByNamespace() throws Exception {
        mockMvc.perform(get("/retailer/getAllRules/{ruleNamespace}","ORDER")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
                );
    }

    @Test
    public void verifyStockCheckRuleOne() throws Exception {
        ProductDetails productDetails = new ProductDetails(
               "A",
                3L);

        MvcResult mvcResult = mockMvc.perform(post("/retailer/checkRule")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productDetails)))
                .andExpect(status().isOk()
                ).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        AdviceDetails adviceDetails = new AdviceDetails("A",
                "Minimum stock level should be 4. Please place order");

        String expectedResponseBody = objectMapper.writeValueAsString(adviceDetails);

        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void verifyStockCheckRuleTwo() throws Exception {
        ProductDetails productDetails = new ProductDetails(
                "A",
                5L);

        MvcResult mvcResult = mockMvc.perform(post("/retailer/checkRule")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productDetails)))
                .andExpect(status().isOk()
                ).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        AdviceDetails adviceDetails = new AdviceDetails(
                "A",
                "No Advice for this Product");

        String expectedResponseBody = objectMapper.writeValueAsString(adviceDetails);

        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void addStockCheckRule() throws Exception {

        RuleDbModel rule = new RuleDbModel(
                RuleNamespace.ORDER.name(),
                "1",
                "(input.productName.equals('A') || input.productName.equals('B') || input.productName.equals('C') || input.productName.equals('E')) && input.stockLevel <= 4",
                "output.setSuggestionMessage('Minimum stock level should be 4. Please place order');output.setProductName(input.productName);",
                1,
                "A minimum stock level of 4.");

        MvcResult mvcResult = mockMvc.perform(post("/retailer/addRule")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rule)))
                .andExpect(status().isOk()
                ).andReturn();


        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    }
    @Test
    public void verifyGetRuleByNameSpace_InvalidId() throws Exception {
        mockMvc.perform(get("/retailer/getAllRules/ruleNamespace"))
                .andExpect(status().is4xxClientError());
    }
    private List<Rule> getListOfRules(){
        Rule rule1 = new Rule(RuleNamespace.ORDER,
                "1",
                "(input.productName.equals('A') || input.productName.equals('B') || input.productName.equals('C') || input.productName.equals('E')) && input.stockLevel <= 4",
                "output.setSuggestionMessage('Minimum stock level should be 4. Please place order');output.setProductName(input.productName);",
                1,
                "A minimum stock level of 4.");

        Rule rule2 = new Rule(RuleNamespace.ORDER,
                "2",
                "(input.productName.equals('D')) && input.stockLevel <= 8 && $(stock.target_done) == false",
                "output.setSuggestionMessage('Minimum stock level should be 8. Please place one-off order of 15');output.setProductName(input.productName);",
                1,
                "A minimum stock level of 8 and one-off order of 15");


        List<Rule> allRulesByNamespace = Lists.newArrayList(rule1, rule2);
        return allRulesByNamespace;
    }
}
