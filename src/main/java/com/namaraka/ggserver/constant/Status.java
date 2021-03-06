package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author smallgod
 */
public enum Status implements Constants {

    NOT_LOGGED("NOT_LOGGED"), //Payment NOT logged to DB for some reason other than 'duplicate', etc
    LOGGED("LOGGED"), //Payment logged to the DB
    PROCESSING("PROCESSING"), //Payment fetched for processing
    SUCCESSFUL("SUCCESSFUL"),
    FAILED("FAILED"),
    REVERSED("REVERSED"),
    DUPLICATE("DUPLICATE"),
    EXPIRED("EXPIRED"),
    UNKNOWN("UNKNOWN"),
    
    
    ALL("ALL"); //used when we need to pick multiple payments with status n- any
    
    private final String statusTypeString;

    private static final Logger logger = LoggerFactory.getLogger(Status.class);

    Status(String statusType) {
        this.statusTypeString = statusType;
    }

    @Override
    public String getValue() {
        return this.statusTypeString;
    }

    public static Status convertToEnum(String statusType) {

        if (statusType != null) {

            for (Status availableStatusType : Status.values()) {

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
