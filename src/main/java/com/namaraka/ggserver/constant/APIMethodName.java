package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public enum APIMethodName implements Constants {

    //APP REQUESTS
//APP REQUESTS
    MAKE_PAYMENT("MAKE_PAYMENT"),
    PAYMENT_HISTORY("PAYMENT_HISTORY"),
    PAYMENT_STATUS("PAYMENT_STATUS"),
    REGISTER_CLIENT("REGISTER_CLIENT"),
    REGISTER_UNIT("REGISTER_UNIT"),
    ACCOUNT_SETUP("ACCOUNT_SETUP"),
    REQUEST_OTP("REQUEST_OTP"),
    ACTIVATION_CODES("ACTIVATION_CODES"),
    SYNC_UNIT("SYNC_UNIT"),
    
    //REPORTS
    REPORT_PAYMENTS("REPORT_PAYMENTS"),
    REPORT_CLIENTS("REPORT_CLIENTS"),
    REPORT_GENERATORS("REPORT_CLIENTS"),
    
    //callback with status from aggregator (MamboPay);
    PAYMENT_STATUS_CALLBACK("PAYMENT_STATUS_CALLBACK");

    private final String methodNameString;

    private static final Logger logger = LoggerFactory.getLogger(APIMethodName.class);

    APIMethodName(String wrapperNodeStr) {
        this.methodNameString = wrapperNodeStr;
    }

    @Override
    public String getValue() {
        return this.methodNameString;
    }

    public static APIMethodName convertToEnum(String methodName) {

        if (methodName != null) {

            for (APIMethodName availableMethodName : APIMethodName.values()) {

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
