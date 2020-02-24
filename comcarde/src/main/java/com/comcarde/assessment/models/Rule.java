package com.comcarde.assessment.models;

import com.comcarde.assessment.enums.RuleNamespace;
import lombok.*;

/**
 *Author : Atul Kumar
 */

@AllArgsConstructor
@Getter
public class Rule {
    RuleNamespace ruleNamespace;
    String ruleId;
    String condition;
    String action;
    Integer priority;
    String description;

}
