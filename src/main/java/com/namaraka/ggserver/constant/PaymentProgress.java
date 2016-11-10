package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author smallgod
 */
public enum PaymentProgress implements Constants {

    NORMAL("NORMAL"),
    OVERDUE("OVERDUE"),
    INARREARS("INARREARS"),
    COMPLETED("COMPLETED");
    
    private final String paymentProgressStr;

    private static final Logger logger = LoggerFactory.getLogger(PaymentProgress.class);

    PaymentProgress(String paymentProgressType) {
        this.paymentProgressStr = paymentProgressType;
    }

    @Override
    public String getValue() {
        return this.paymentProgressStr;
    }

    public static PaymentProgress convertToEnum(String paymentProgressType) {

        if (paymentProgressType != null) {

            for (PaymentProgress availPaymentProgressType : PaymentProgress.values()) {

                if (paymentProgressType.equalsIgnoreCase(availPaymentProgressType.getValue())) {
                    return availPaymentProgressType;
                }
            }
        }
        logger.warn("No constant with text " + paymentProgressType + " found");
        throw new IllegalArgumentException("No constant with text " + paymentProgressType + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + paymentProgressType, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
