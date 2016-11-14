package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In order to possess the generator unit, you must be registered as a customer to the generator.
 * Customers can be either end-users, distributors, manufacturer or a Telesola personnel
 * @author smallgod
 */


public enum Region implements Constants {

    KAMPALA("KLA"),
    MBALE("MLE"),
    LIRA("LIR"),
    FORTPORTAL("FTP");

    private final String region;

    private static final Logger logger = LoggerFactory.getLogger(Region.class);

    Region(String region) {
        this.region = region;
    }

    @Override
    public String getValue() {
        return this.region;
    }

    public static Region convertToEnum(String region) {

        if (region != null) {

            for (Region regionVal : Region.values()) {

                if (region.equalsIgnoreCase(regionVal.getValue())) {
                    return regionVal;
                }
            }
        }
        logger.warn("No constant with text " + region + " found");
        throw new IllegalArgumentException("No constant with text " + region + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + region, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
