package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the different states a processing unit such as a monitoring unit or AdDisplay unit 
 * could be in at any one moment
 * @author smallgod
 */
public enum APIContentType implements Constants {

    JSON("application/json"), 
    XML("application/xml"),
    HTTP("HTTP"); 
    
    private final String apiType;

    private static final Logger logger = LoggerFactory.getLogger(APIContentType.class);

    APIContentType(String apiType) {
        this.apiType = apiType;
    }

    @Override
    public String getValue() {
        return this.apiType;
    }

    public static APIContentType convertToEnum(String givenApiType) {

        if (givenApiType != null) {

            for (APIContentType knownApiType : APIContentType.values()) {

                if (knownApiType.getValue().equalsIgnoreCase(givenApiType)) {
                    return knownApiType;
                }
            }
        }
        logger.warn("No constant with text " + givenApiType + " found");
        throw new IllegalArgumentException("No constant with text " + givenApiType + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + givenApiType, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
