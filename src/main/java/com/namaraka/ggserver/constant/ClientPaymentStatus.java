package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author smallgod
 */
public enum ClientPaymentStatus implements Constants {

    PAID_FULLY_UPFRONT("PAID_FULLY_UPFRONT"),
    INSTALLMENTS_PENDING("INSTALLMENTS_PENDING"),
    INSTALLMENTS_COMPLETED("INSTALLMENTS_COMPLETED"),
    WRITTEN_OFF("WRITTEN_OFF");
    
    private final String clientPaymentStatus;

    private static final Logger logger = LoggerFactory.getLogger(ClientPaymentStatus.class);

    ClientPaymentStatus(String clientPayStatus) {
        this.clientPaymentStatus = clientPayStatus;
    }

    @Override
    public String getValue() {
        return this.clientPaymentStatus;
    }

    public static ClientPaymentStatus convertToEnum(String statusType) {

        if (statusType != null) {

            for (ClientPaymentStatus clientPayStatus : ClientPaymentStatus.values()) {

                if (statusType.equalsIgnoreCase(clientPayStatus.getValue())) {
                    return clientPayStatus;
                }
            }
        }
        logger.warn("No constant with text " + statusType + " found");
        throw new IllegalArgumentException("No constant with text " + statusType + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + statusType, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
