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
public enum PaymentsMadeJson implements Constants {

    USER_ID("user_id"),
    DEFAULT("UNKNOWN");

    private final String paymentsMade;
    private static final Logger logger = LoggerFactory.getLogger(PaymentsMadeJson.class);

    PaymentsMadeJson(String userJsonKey) {
        this.paymentsMade = userJsonKey;
    }

    @Override
    public String getValue() {
        return this.paymentsMade;
    }

    public static PaymentsMadeJson convertToEnum(String paymentJsonKeyVal) {

        if (paymentJsonKeyVal != null) {

            for (PaymentsMadeJson jsonKeyEnum : PaymentsMadeJson.values()) {
                if (paymentJsonKeyVal.equalsIgnoreCase(jsonKeyEnum.getValue())) {
                    return jsonKeyEnum;
                }
            }
        }
        logger.warn("No constant with text " + paymentJsonKeyVal + " found");
        return DEFAULT;
    }

}
