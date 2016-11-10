/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.constant;

/**
 *
 * @author smallgod
 */
public interface NamedConstants {

    /**
     * Date time string formats
     *
     * public static final String timeMinAndSecFormat = "HH:mm:ss"; public
     * static final String timeAndMinutesFormat = "HH:mm"; public static final
     * String dateOnlyFormat = "dd/MM/yyyy"; public static final String
     * dateOnlyDashFormat = "dd-MM-yyyy"; public static final String
     * dateTimeFormat = "dd/MM/yyyy HH:mm"; public static final String
     * dateOnlyDashYearFirstFormat = "yyyy-MM-dd";
    *
     */
    public static final String KAMPALA_TIME_ZONE = "Africa/Kampala";

    /**
     * The Date format we are using in this application is in the format
     * "2016-07-25 08:55:09"
     */
    public static final String DATE_TIME_DASH_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * All JSON request strings must have a root node named 'method' which
     * identifies the name of the API method being called
     */
    public static final String JSON_METHOD_NODENAME = "method";

    /**
     * All JSON request strings must have a root node named 'params' which
     * identifies the parameters of the API method being called
     */
    public static final String JSON_PARAMS_NODENAME = "params";

    /**
     * When storing appconfigs in the servlet context, we will use this as the
     * attribute name
     */
    public static final String APPCONFIGS_ATTR_NAME = "appconfigs";

    public static final String APPCONFIGS_FILE = "appconfigs/appconfigs.xsd";

    /**
     * When storing the database reference in the servlet context, we will use
     * this as the attribute name
     */
    public static final String DATABASE_ATTR_NAME = "dbreference";

    /**
     * When storing the http pool reference in the servlet context, we will use
     * this as the attribute name
     */
    public static final String HTTP_POOL_ATTR_NAME = "httppool";

    public static final String ADCENTRAL_JSON_HANDLER = "adcentraljsonhandler";

    public static final String ADDISPLAY_JSON_HANDLER = "addisplayjsonhandler";

    public static final String AD_DBMANAGER_JSON_HANDLER = "addbmanagerjsonhandler";

    /**
     * A username that last modified a given auditable database entity
     */
    public static final String PROPNAME_LAST_MODIFIED_BY = "lastModifiedBy";

    /**
     * A date when a given auditable database entity was modified
     */
    public static final String PROPNAME_DATE_LAST_MODIFIED = "dateLastModified";

    /**
     * A delimited string with a trail of dates when an auditable database
     * entity has been modified
     */
    public static final String PROPNAME_DATE_MODIFIED_HISTORY = "dateModifiedHistory";
    /**
     * A delimited string with a trail of usernames that have modified an
     * auditable database entity
     */
    public static final String PROPNAME_MODIFIED_BY_HISTORY = "modifiedByHistory";

    /**
     * A username that first created a given auditable database entity
     */
    public static final String PROPNAME_CREATED_BY = "createdBy";

    /**
     * A date when a given auditable database entity was first created
     */
    public static final String PROPNAME_CREATED_ON = "createdOn";

    /**
     * same as the JDBC batch size in the hibernate config xml config file Will
     * flush a batch of inserts and release memory
     */
    public static final int HIBERNATE_JDBC_BATCH = 30;

    /**
     * The batch size for number of records to be read at a time
     */
    public static final int NUM_ROWS_CHUNK = 10000;

    /**
     * Profile under which the daemon has been started and is running -
     * Development profile
     */
    public static final String DAEMON_PROFILE_DEV = "development";

    /**
     * Profile under which the daemon has been started and is running -
     * Production profile
     */
    public static final String DAEMON_PROFILE_PROD = "production";

    /**
     * Profile under which the daemon has been started and is running - Staging
     * profile
     */
    public static final String DAEMON_PROFILE_STAGE = "staging";

    /**
     * Http header value for the JSON content type
     */
    //public static final String HTTP_CONTENT_TYPE_JSON = "application/json"; //"application/json;charset=UTF-8")
    /**
     * Http header value for the XML content type
     */
    //public static final String HTTP_CONTENT_TYPE_XML = "application/xml";
    /**
     * An http server can return a success http response but with no content in
     * the body
     */
    public static final int HTTPCODE_SUCCESS_NO_CONTENT = 204;

    /**
     * HTTP requestmethod - POST
     */
    public static final String HTTP_REQUESTMETHOD_POST = "POST";
    /**
     * HTTP requestmethod - GET
     */
    public static final String HTTP_REQUESTMETHOD_GET = "GET";

    /**
     * Attribute name for the http parameter name holding the database entity
     * name/type useful when passing a request to the DB manager unit, so that
     * the entity in context can be idenitified
     */
    public static final String HTTP_PARAM_ENTITYNAME = "entityname";

    /**
     * HTTP parameter name with value to identify which DB operation is being
     * executed
     */
    public static final String HTTP_PARAM_DB_OPERATION = "dboperation";

}
