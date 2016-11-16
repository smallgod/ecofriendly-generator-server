package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public enum HTTPMethod implements Constants {

    GET("GET"),
    POST("POST");

    private final String httpMethodStr;

    private static final Logger logger = LoggerFactory.getLogger(HTTPMethod.class);

    HTTPMethod(String wrapperNodeStr) {
        this.httpMethodStr = wrapperNodeStr;
    }

    @Override
    public String getValue() {
        return this.httpMethodStr;
    }

    public static HTTPMethod convertToEnum(String methodName) {

        if (methodName != null) {

            for (HTTPMethod availableMethodName : HTTPMethod.values()) {

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
