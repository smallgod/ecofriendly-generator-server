package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public enum RepaymentPeriod implements Constants {

    SIX_MONTHS(6),
    TWELVE_MONTHS(12),
    EIGHTEEN_MONTHS(18),;

    private final int repaymentPeriod;

    private static final Logger logger = LoggerFactory.getLogger(RepaymentPeriod.class);

    RepaymentPeriod(int repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    @Override
    public String getValue() {
        return String.valueOf(this.repaymentPeriod);
    }
    
    
    public int getIntValue(){
        return this.repaymentPeriod;
    }

    public static RepaymentPeriod convertToEnum(int repayPeriod) {

        if (repayPeriod > 0) {

            for (RepaymentPeriod availableRepayPeriods : RepaymentPeriod.values()) {

                if (repayPeriod == availableRepayPeriods.getIntValue()) {
                    return availableRepayPeriods;
                }
            }
        }
        logger.warn("No constant with text " + repayPeriod + " found");
        throw new IllegalArgumentException("No constant with text " + repayPeriod + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + repayPeriod, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
