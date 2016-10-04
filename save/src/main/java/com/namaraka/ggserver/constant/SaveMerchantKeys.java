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
public enum SaveMerchantKeys implements Constants {

    MERCHANT_VSTORE("valuestore"),
    MERCHANT_CONTACT("merchant_contact"),
    SETTLEMENT_ACCOUNT("settlement_account"),
    MERCHANT_ATTRIBUTES("identityAttributes"),
    MERCHANT_HANDLE("merchant_handle"),
    ACQUISITION_ID("acquisition_id"),
    MERCHANT_NAME("merchant_name"),
    TRANS_ID("transaction_id"),
    MERCHANT_QUERIES("merchantQueries"),
    SERVICE_TYPE("service_type"), //GOODS | SERVICES
    CUSTOMER_LIST("client_list"),
    DEFAULT("UNKNOWN");

    private final String saveMerchantKey;
    private static final Logger logger = LoggerFactory.getLogger(SaveMerchantKeys.class);

    SaveMerchantKeys(String paymentJsonKey) {
        this.saveMerchantKey = paymentJsonKey;
    }

    @Override
    public String getValue() {
        return this.saveMerchantKey;
    }

    public static SaveMerchantKeys convertToEnum(String paymentJsonKeyVal) {

        if (paymentJsonKeyVal != null) {

            for (SaveMerchantKeys jsonKeyEnum : SaveMerchantKeys.values()) {
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
