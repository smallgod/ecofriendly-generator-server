package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author smallgod
 */
public enum PaymentStatus implements Constants {

    LOGGED("LOGGED"), //Payment logged to the DB
    NOT_LOGGED("NOT_LOGGED"), //Payment NOT logged to DB for some reason
    PROCESSING("PROCESSING"), //Payment fetched for processing
    SUCCESSFUL("SUCCESSFUL"),
    FAILED("FAILED"),
    EXPIRED("EXPIRED"),
    UNKNOWN("UNKNOWN");
    
    private final String methodNameString;

    private static final Logger logger = LoggerFactory.getLogger(PaymentStatus.class);

    PaymentStatus(String wrapperNodeStr) {
        this.methodNameString = wrapperNodeStr;
    }

    @Override
    public String getValue() {
        return this.methodNameString;
    }

    public static PaymentStatus convertToEnum(String methodName) {

        if (methodName != null) {

            for (PaymentStatus availableMethodName : PaymentStatus.values()) {

                if (methodName.equalsIgnoreCase(availableMethodName.getValue())) {
                    return availableMethodName;
                }
            }
        }
        logger.warn("No constant with text " + methodName + " found");
        throw new IllegalArgumentException("No constant with text " + methodName + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + methodName, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
