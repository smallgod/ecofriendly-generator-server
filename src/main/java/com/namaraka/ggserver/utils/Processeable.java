/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.utils;

/**
 *
 * @author smallgod
 */
public interface Processeable extends DBMSXMLObject{
    
    //a processeable object must have a generated InternalTxnID
    
    String getInternalTransactionID();
}

