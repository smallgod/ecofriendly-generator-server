/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.utils;

import com.namaraka.ggserver.jsondata.PaymentHistoryResponse;
import java.util.Comparator;

/**
 *
 * @author smallgod
 */
public class PayHistoryUnitComparator implements Comparator<PaymentHistoryResponse.Unit> {

    @Override
    public int compare(PaymentHistoryResponse.Unit o1, PaymentHistoryResponse.Unit o2) {

        if (o1 == null || o2 == null) {

            //throw new Exception("Failed to compare 2 MoMoPayment objects");
        }

        // TODO: Handle null x or y values
        return ((new Long(o2.getId()).compareTo(o1.getId())));

        //int startComparison = compare(x.timeStarted, y.timeStarted);
        //return startComparison != 0 ? startComparison : compare(x.timeEnded, y.timeEnded);
    }

    // I don't know why this isn't in Long...
    private static int compare(long a, long b) {
        return a < b ? -1 : a > b ? 1 : 0;
    }

}
