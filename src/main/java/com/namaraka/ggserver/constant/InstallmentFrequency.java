package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public enum InstallmentFrequency implements Constants {

    WEEKLY("WEEKLY"),
    BIWEEKLY("BIWEEKLY"),
    MONTHLY("MONTHLY");

    private final String installmentFrequencyStr;

    private static final Logger logger = LoggerFactory.getLogger(InstallmentFrequency.class);

    InstallmentFrequency(String installmentFrequencyType) {
        this.installmentFrequencyStr = installmentFrequencyType;
    }

    @Override
    public String getValue() {
        return this.installmentFrequencyStr;
    }

    public static InstallmentFrequency convertToEnum(String installmentFrequencyType) {

        if (installmentFrequencyType != null) {

            for (InstallmentFrequency availableInstallmentFreq : InstallmentFrequency.values()) {

                if (installmentFrequencyType.equalsIgnoreCase(availableInstallmentFreq.getValue())) {
                    return availableInstallmentFreq;
                }
            }
        }
        logger.warn("No constant with text " + installmentFrequencyType + " found");
        throw new IllegalArgumentException("No constant with text " + installmentFrequencyType + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + installmentFrequencyType, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
