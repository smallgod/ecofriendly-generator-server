package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author smallgod
 */
public enum OrderFirst implements Constants {

    OLDEST("OLDEST"),
    NEWEST("NEWEST");

    private final String orderFirst;

    private static final Logger logger = LoggerFactory.getLogger(OrderFirst.class);

    OrderFirst(String orderFirst) {
        this.orderFirst = orderFirst;
    }

    @Override
    public String getValue() {
        return this.orderFirst;
    }

    public static OrderFirst convertToEnum(String orderFirst) {

        if (orderFirst != null) {

            for (OrderFirst orderFirstVal : OrderFirst.values()) {

                if (orderFirst.equalsIgnoreCase(orderFirstVal.getValue())) {
                    return orderFirstVal;
                }
            }
        }
        logger.warn("No constant with text " + orderFirst + " found");
        throw new IllegalArgumentException("No constant with text " + orderFirst + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + orderFirst, ErrorCategory.CLIENT_ERR_TYPE);
    }
}
