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

    //To-Do - Put these in configs file
    public static final String GGSERVER_CALLBACK_URL = "http://namaraka.com:9099/api/json";
    public static final String MAMBOPAY_DEBIT_URL = "https://mambopay.azure-api.net/api/v1/mtnmobilemoneyapi/debit";
    public static final String SUBSCRIPTION_KEY = "9389259ec349469682c71910ab6f4ac3";

    public static final String START_ID = "00001"; //Will give us 3.7million ids before introducing 6th digit goes upto YYYYZ
    public static final int MAX_NUMBER_PAYMENTS = 5;
    public static final OrderFirst ORDER_FIRST = OrderFirst.NEWEST;

    public static final int NUM_OF_ACTIVATION_CODES = 100;

    public static final int MAMBOPAY_DEBIT_ACCOUNT_UNREGISTERED = 101;
    public static final int MAMBOPAY_DEBIT_INSUFFICIENT_FUNDS = 106;
    public static final int MAMBOPAY_DEBIT_BELOW_THRESHOLD = 105;
    public static final int MAMBOPAY_DEBIT_CUSTOMER_UNAPPROVED = 103;
    public static final int MAMBOPAY_DEBIT_SUCCESS = 1;
    public static final String MAMBOPAY_DEBIT_PROCESSING = "01";
    public static final String MAMBOPAY_DEBIT_DUPLICATE = "02";

    //MAMBO-PAY DEBIT API HTTP PARAMS
    public static final String MAMBOPAY_PARAM_MSISDN = "msisdn";
    public static final String MAMBOPAY_PARAM_AMOUNT = "amount";
    public static final String MAMBOPAY_PARAM_TRANSID = "transaction_id";
    public static final String MAMBOPAY_PARAM_CALLBACKURL = "callbackurl";
    public static final String MAMBOPAY_HEADER_SUBSCKEY = "Ocp-Apim-Subscription-Key";

    public static final String SMS_API_URL = "http://api.infobip.com/api/v3/sendsms/plain";
    public static final String SMS_API_SENDER_NAME = "TELESOLA";
    public static final String SMS_API_USERNAME = "codev";
    public static final String SMS_API_PASSWORD = "codev";
    public static final String SMS_TEMPLATE_ACT_CODE ="Hello {firstName}, you have paid {amount}, remaining balance is: {outstandingBalance}. Enter this code: {activationCode}, in your Telesola app to activate {numberOfActiveDays} days. Thank you.";
    public static final String SMS_PAYMENT_FAILURE ="Hello {firstName}, payment inititiated for generator ID: {generatorId}, has Failed. Reason: {statusDescription}. Please initiate/approve another payment to activate your generator. Thank you.";
    public static final String SMS_TEMPLATE_OTP = "Hello {firstName}, your unit details are; One-time PIN: {otp}, Telesola account: {telesolaAccount}, unit ID: {generatorId}. Please enter the details to activate your app";

    public static final String SMS_API_PARAM_USERNAME = "user";
    public static final String SMS_API_PARAM_PASSOWRD = "password";
    public static final String SMS_API_PARAM_SENDER = "sender";
    public static final String SMS_API_PARAM_TEXT = "SMSText";
    public static final String SMS_API_PARAM_RECIPIENT = "GSM";

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
     * The DateTime format we are using in this application is in the format
     * "2016-07-25 08:55:09"
     */
    public static final String DATE_TIME_DASH_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    
    /**
     * The Date only format we are using in this application is in the format
     * "2016-07-25"
     */
    public static final String DATE_DASH_FORMAT = "yyyy-MM-dd";

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
