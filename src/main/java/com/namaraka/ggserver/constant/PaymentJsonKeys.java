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
public enum PaymentJsonKeys implements Constants {

    AMOUNT("amount"),
    PAYER_ACCOUNT("payer_account"),
    PAYER_NARRATION("payer_narration"),
    RECIEVER_HANDLE("reciever_handle"),
    RECIEVER_ALIAS("reciever_alias"),
    PAYMENT_CHANNEL("payment_channel"),
    PAYMENT_QA("payment_queries"),
    TRANS_ID("transaction_id"),
    RECIEVER_DETAILS("reciever_details"),
    SERVICE_TYPE("service_type"), //GOODS | SERVICES
    PAYER_ID("user_id"),
    PAYER_NAMES("payer_names"),
    DEFAULT("UNKNOWN");

    private final String paymentJsonKey;
    private static final Logger logger = LoggerFactory.getLogger(PaymentJsonKeys.class);

    PaymentJsonKeys(String paymentJsonKey) {
        this.paymentJsonKey = paymentJsonKey;
    }

    @Override
    public String getValue() {
        return this.paymentJsonKey;
    }

    public static PaymentJsonKeys convertToEnum(String paymentJsonKeyVal) {

        if (paymentJsonKeyVal != null) {

            for (PaymentJsonKeys jsonKeyEnum : PaymentJsonKeys.values()) {
                if (paymentJsonKeyVal.equalsIgnoreCase(jsonKeyEnum.getValue())) {
                    return jsonKeyEnum;
                }
            }
        }
        logger.warn("No constant with text " + paymentJsonKeyVal + " found");
        return DEFAULT;
        //throw new IllegalArgumentException("No constant with text " + paymentJsonKeyVal + " found");
        //return null;
    }

}
