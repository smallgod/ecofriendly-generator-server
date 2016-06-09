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
public enum UserJsonKeys implements Constants {

    USER_MSISDN("user_msisdn"),
    USER_ID("user_id"),
    DEFAULT("UNKNOWN");

    private final String userJsonKey;
    private static final Logger logger = LoggerFactory.getLogger(UserJsonKeys.class);

    UserJsonKeys(String userJsonKey) {
        this.userJsonKey = userJsonKey;
    }

    @Override
    public String getValue() {
        return this.userJsonKey;
    }

    public static UserJsonKeys convertToEnum(String paymentJsonKeyVal) {

        if (paymentJsonKeyVal != null) {

            for (UserJsonKeys jsonKeyEnum : UserJsonKeys.values()) {
                if (paymentJsonKeyVal.equalsIgnoreCase(jsonKeyEnum.getValue())) {
                    return jsonKeyEnum;
                }
            }
        }
        logger.warn("No constant with text " + paymentJsonKeyVal + " found");
        return DEFAULT;
    }

}
