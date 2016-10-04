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
public enum ValueStore implements Constants {

    MTNMONEY("MTNMONEY"),
    AIRTELMONEY("AIRTELMONEY"),
    CENTEMONEY("CENTEMONEY"),
    CELLUMONEY("CELLUMONEY"),
    CASHMONEY("CASHMONEY"),
    STANBICMONEY("STANBICMONEY"),
    UNKNOWNSTORE("UKNOWNSTORE");

    private final String valueStore;
    private static final Logger logger = LoggerFactory.getLogger(ValueStore.class);

    ValueStore(String valueStore) {
        this.valueStore = valueStore;
    }

    @Override
    public String getValue() {
        return this.valueStore;
    }

    /**
     * Get Value Store Enum corresponding to given string
     * @param storeTypeValue
     * @return 
     */
    public static ValueStore convertToEnum(String storeTypeValue) {

        if (storeTypeValue != null) {

            for (ValueStore valueStoreEnum : ValueStore.values()) {
                
                if (storeTypeValue.equalsIgnoreCase(valueStoreEnum.getValue())) {

                    return valueStoreEnum;
                }
            }
        }

        logger.warn("No constant with text " + storeTypeValue + " found");
        return UNKNOWNSTORE;
    }
}
