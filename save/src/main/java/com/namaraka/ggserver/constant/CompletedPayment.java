/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public enum CompletedPayment implements Constants {

    TRANSACTION_ID("transaction_id"),
    ACQUISITION_ID("acquisition_id"),
    STATUS("status"),
    NARRATION("narration"),
    DEFAULT("UNKNOWN");

    private final String completedPayment;
    private static final Logger logger = LoggerFactory.getLogger(CompletedPayment.class);

    CompletedPayment(String userJsonKey) {
        this.completedPayment = userJsonKey;
    }

    @Override
    public String getValue() {
        return this.completedPayment;
    }

    public static CompletedPayment convertToEnum(String paymentJsonKeyVal) {

        if (paymentJsonKeyVal != null) {

            for (CompletedPayment jsonKeyEnum : CompletedPayment.values()) {
                if (paymentJsonKeyVal.equalsIgnoreCase(jsonKeyEnum.getValue())) {
                    return jsonKeyEnum;
                }
            }
        }
        logger.warn("No constant with text " + paymentJsonKeyVal + " found");
        return DEFAULT;
    }

}
