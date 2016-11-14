package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A client can be warned, suspended or banned from ever getting another unit
 * because of defaulting on payment or mishandling units (for case of
 * distributors or Telesola employees)
 *
 * @author smallgod
 */
public enum ClientStatus implements Constants {

    NORMAL("INSTALLMENTS_COMPLETED"),
    WARNED("WARNED"),
    SUSPENDED("INSTALLMENTS_PENDING"),
    BANNED("PAID_FULLY_UPFRONT");

    private final String clientStatus;

    private static final Logger logger = LoggerFactory.getLogger(ClientStatus.class);

    ClientStatus(String status) {
        this.clientStatus = status;
    }

    @Override
    public String getValue() {
        return this.clientStatus;
    }

    public static ClientStatus convertToEnum(String clientStatus) {

        if (clientStatus != null) {

            for (ClientStatus clientStatusVal : ClientStatus.values()) {

                if (clientStatus.equalsIgnoreCase(clientStatusVal.getValue())) {
                    return clientStatusVal;
                }
            }
        }
        logger.warn("No constant with text " + clientStatus + " found");
        throw new IllegalArgumentException("No constant with text " + clientStatus + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + clientStatus, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
