package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author smallgod
 */
public enum CommercialStatus implements Constants {

    INSTALLMENT("INSTALLMENT"), // Customer is paying for the unit in installment
    SOLD("SOLD"); // customer has fully (100%) paid for the unit.
    
    private final String statusTypeString;

    private static final Logger logger = LoggerFactory.getLogger(CommercialStatus.class);

    CommercialStatus(String statusType) {
        this.statusTypeString = statusType;
    }

    @Override
    public String getValue() {
        return this.statusTypeString;
    }

    public static CommercialStatus convertToEnum(String statusType) {

        if (statusType != null) {

            for (CommercialStatus availableStatusType : CommercialStatus.values()) {

                if (statusType.equalsIgnoreCase(availableStatusType.getValue())) {
                    return availableStatusType;
                }
            }
        }
        logger.warn("No constant with text " + statusType + " found");
        throw new IllegalArgumentException("No constant with text " + statusType + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + statusType, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
