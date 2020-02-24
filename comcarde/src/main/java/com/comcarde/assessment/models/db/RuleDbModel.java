package com.comcarde.assessment.models.db;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 *Author : Atul Kumar
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rules")
@IdClass(RuleDbModel.IdClass.class)
public class RuleDbModel {
    @Id
    @Column(name = "rule_namespace")
    private String ruleNamespace;

    @Id
    @Column(name = "rule_id")
    private String ruleId;

    @Column(name = "condition")
    private String condition;

    @Column(name = "action")
    private String action;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "description")
    private String description;

    @Setter
    static class IdClass implements Serializable {
        private String ruleNamespace;
        private String ruleId;
    }
}
