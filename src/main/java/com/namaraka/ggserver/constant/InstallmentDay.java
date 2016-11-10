package com.namaraka.ggserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public enum InstallmentDay implements Constants {

    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6),
    SEVENTH(7),
    EIGHT(8),
    NINTH(9),
    TENTH(10),
    ELEVENTH(11),
    TWELVETH(12),
    THIRTEENTH(13),
    FOURTEENTH(14),
    FIFTEENTH(15),
    SIXTEENTH(16),
    SEVENTEENTH(17),
    EIGHTEENTH(18),
    NINETEENTH(19),
    TWENTIETH(20),
    TWENTYFIRST(21),
    TWENTYSECOND(22),
    TWENTYTHIRD(23),
    TWENTYFOUTH(24),
    TWENTYFIFTH(25),
    TWENTYSIXTH(26),
    TWENTYSEVENTH(27),
    TWENTYEIGHTH(28),
    TWENTYNINTH(29),
    THIRTIETH(30),
    THIRTYFIRST(31);

    private final int installmentDayStr;

    private static final Logger logger = LoggerFactory.getLogger(InstallmentDay.class);

    InstallmentDay(int installmentDayType) {
        this.installmentDayStr = installmentDayType;
    }

    @Override
    public String getValue() {
        return String.valueOf(this.installmentDayStr);
    }

    public static InstallmentDay convertToEnum(String installmentDayType) {

        if (installmentDayType != null) {

            for (InstallmentDay availInstallmentDayType : InstallmentDay.values()) {

                if (installmentDayType.equalsIgnoreCase(availInstallmentDayType.getValue())) {
                    return availInstallmentDayType;
                }
            }
        }
        logger.warn("No constant with text " + installmentDayType + " found");
        throw new IllegalArgumentException("No constant with text " + installmentDayType + " found");
        //throw new MyCustomException("Unsupported Status Exception", ErrorCode.NOT_SUPPORTED_ERR, "Unsupported status value :: " + installmentDayType, ErrorCategory.CLIENT_ERR_TYPE);

    }
}
