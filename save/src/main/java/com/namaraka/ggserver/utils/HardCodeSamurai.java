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
public interface HardCodeSamurai {
    
    public static final String HIBERNATE_PROPS_FILE = "/etc/configs/ug/aopc/hibernate.cfg.xml";
    public static final String CUSTOM_TYPES_FILE = "/etc/configs/ug/aopc/CustomTypes.hbm.xml";
    public static final String APPCONFIGS_FILE = "/etc/configs/ug/aopc/appconfigs.xml";
    public static final String LOG4J_PROPS_FILE = "/etc/configs/ug/aopc/log4j.xml";
    public static final String XSD_FILES_FOLDER = "/etc/xsdfiles/ug/aopc/v1_0/";
    
    
    public static final int NUM_ROWS_CHUNK = 10000; //The batch size for number of records to be read at a time
    public static final int INDICATE_AT = 10; //The progress indicator moves every 10 records read
    public static final int HIBERNATE_JDBC_BATCH = 30; //same as the JDBC batch size in the hibernate config xml config file

    
    
}
