package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In order to possess the generator unit, you must be registered as a customer to the generator.
 * Customers can be either end-users, distributors, manufacturer or a Telesola personnel
 * @author smallgod
 */


public enum ClientType implements Constants {

    MANUFACTURER("M"),
    TELESOLA("T"),
    DISTRIBUTOR("D"),
    CUSTOMER("C");

    private final String clientType;

    private static final Logger logger = LoggerFactory.getLogger(ClientType.class);

    ClientType(String clientType) {
        this.clientType = clientType;
    }

    @Override
    public String getValue() {
        return this.clientType;
    }

    public static ClientType convertToEnum(String clientType) {

        if (clientType != null) {

            for (ClientType clientTypeVal : ClientType.values()) {

                logger.debug("checking ClientType val: " + clientTypeVal + " against: " + clientTypeVal.getValue());
                
                if (clientType.equalsIgnoreCase(clientTypeVal.getValue())) {
                    return clientTypeVal;
                }
            }
        }
        logger.warn("No constant with text " + clientType + " found");
        throw new IllegalArgumentException("No constant with text " + clientType + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + clientType, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
