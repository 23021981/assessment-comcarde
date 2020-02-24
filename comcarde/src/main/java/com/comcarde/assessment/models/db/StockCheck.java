package com.comcarde.assessment.models.db;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 *Author : Atul Kumar
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "StockCheckAudit")
public class StockCheck {

    @Column(name = "productName")
    String productName;
    @Column(name = "stockCheck")
    Boolean stockCheck;
    @Column(name = "recommended")
    Boolean recommended;
    @Id
    @Column(name = "checkTimeStamp")
    Timestamp checkTimeStamp;
    @Column(name = "advice")
    String adviceMessage;

}
