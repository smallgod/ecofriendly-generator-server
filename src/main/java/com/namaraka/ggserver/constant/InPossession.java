package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public enum InPossession implements Constants {

    MANUFACTURER("MANUFACTURER"),
    TELESOLA("INSTALLMENT"),
    DISTRIBUTOR("SOLD"),
    CUSTOMER("CUSTOMER");

    private final String inPossessionTypeStr;

    private static final Logger logger = LoggerFactory.getLogger(InPossession.class);

    InPossession(String inPossessionType) {
        this.inPossessionTypeStr = inPossessionType;
    }

    @Override
    public String getValue() {
        return this.inPossessionTypeStr;
    }

    public static InPossession convertToEnum(String inPossessionType) {

        if (inPossessionType != null) {

            for (InPossession availPossessionType : InPossession.values()) {

                if (inPossessionType.equalsIgnoreCase(availPossessionType.getValue())) {
                    return availPossessionType;
                }
            }
        }
        logger.warn("No constant with text " + inPossessionType + " found");
        throw new IllegalArgumentException("No constant with text " + inPossessionType + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + inPossessionType, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
