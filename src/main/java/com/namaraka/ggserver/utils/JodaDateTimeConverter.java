/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.utils;

import org.joda.time.DateTime;

/**
 *
 * @author smallgod
 */
public class JodaDateTimeConverter {

    public static String printDate(DateTime value) {
        return value.toString();
    }

    public static DateTime parseDate(String value) {
        return new DateTime(value);
    }

}
