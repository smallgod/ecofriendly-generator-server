/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.utils;

import org.stringtemplate.v4.ST;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.namaraka.ggserver.AppEntry;
import com.namaraka.ggserver.constant.APIContentType;
import com.namaraka.ggserver.constant.ClientType;
import com.namaraka.ggserver.constant.CommercialStatus;
import com.namaraka.ggserver.constant.HTTPMethod;
import com.namaraka.ggserver.constant.InstallmentFrequency;
import com.namaraka.ggserver.constant.NamedConstants;
import com.namaraka.ggserver.dbaccess.DBManager;
import com.namaraka.ggserver.model.v1_0.Amounttype;
import com.namaraka.ggserver.model.v1_0.ActivationCodeTracker;
import com.namaraka.ggserver.model.v1_0.OneTimePin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.openide.util.MapFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public class GeneralUtils {

    private static final Logger logger = LoggerFactory.getLogger(GeneralUtils.class);

    private static final Type stringMapType = new TypeToken<Map<String, Object>>() {
    }.getType();

    private static final Type mapInMapType = new TypeToken<Map<String, Map<String, String>>>() {
    }.getType();

    /**
     * Convert a JSON string to pretty print version
     *
     * @param jsonString
     * @return a well formatted JSON string
     */
    public static String toPrettyJsonFormat(String jsonString) {
        JsonParser parser = new JsonParser();

        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    /**
     * Get the JSON string from an HTTPServerletRequest
     *
     * @param request
     * @return
     * @throws MyCustomException
     */
    public static String getJsonStringFromRequest(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        String s;

        try {

            reader = request.getReader();

            do {

                s = reader.readLine();

                if (s != null) {
                    sb.append(s);
                } else {
                    break;
                }

            } while (true);

        } catch (IOException ex) {
            logger.error("IO Exception, failed to decode JSON string from request: " + ex.getMessage());
            //throw new MyCustomException("IO Exception occurred", ErrorCode.CLIENT_ERR, "Failed to decode JSON string from the HTTP request: " + ex.getMessage(), ErrorCategory.CLIENT_ERR_TYPE);

        } catch (Exception ex) {
            logger.error("General Exception, failed to decode JSON string from request: " + ex.getMessage());
            //throw new MyCustomException("General Exception occurred", ErrorCode.CLIENT_ERR, "Failed to decode JSON string from the HTTP request: " + ex.getMessage(), ErrorCategory.CLIENT_ERR_TYPE);

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    logger.error("exception closing buffered reader: " + ex.getMessage());
                }
            }
        }

        return sb.toString();
    }

    /**
     * Write a response to calling server client
     *
     * @param response
     * @param responseToWrite
     * @throws com.namaraka.recon.exceptiontype.MyCustomException
     */
    public static void writeResponse(HttpServletResponse response, String responseToWrite) {

        PrintWriter out = null;

        try {

            out = response.getWriter();
            out.write(responseToWrite);
            out.flush();
            response.flushBuffer();

        } catch (IOException ex) {

            //throw new MyCustomException("Error writing response to client", ErrorCode.COMMUNICATION_ERR, ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
            logger.error("Error writing response to client: " + ex.getMessage());

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Return JSON string representation of given object
     *
     * @param <T>
     * @param objectToConvert
     * @param objectType
     * @return
     */
    public static <T> String convertToJson(Object objectToConvert, Class<T> objectType) {

        Gson gson = new Gson();
        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.toJson(objectToConvert, objectType);
    }

    /**
     * Return JSON string representation of given object
     *
     * @param <T>
     * @param objectToConvert
     * @param objectType
     * @return
     */
    public static <T> String convertToJson(Object objectToConvert, Type objectType) {

        Gson gson = new Gson();
        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

        return gson.toJson(objectToConvert, objectType);
    }

    /**
     * Return Object from JSON string
     *
     * @param <T>
     * @param stringToConvert
     * @param objectType
     * @return
     * @throws com.namaraka.recon.exceptiontype.MyCustomException
     */
    public static <T> T convertFromJson(String stringToConvert, Class<T> objectType) {

        Gson gson = new Gson();
        T returnObj = null;

        try {
            returnObj = gson.fromJson(stringToConvert.trim(), objectType);

        } catch (JsonSyntaxException jse) {
            logger.error("JSON Syntax Error while converting from JSON: " + jse.getMessage());

            //throw new MyCustomException("JSON Syntax Error", ErrorCode.INTERNAL_ERR, "Json syntax error converting from JSON: " + jse.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
        }

        return returnObj;
    }

    /**
     *
     * @param <T>
     * @param stringArrayToConvert
     * @param objectType
     * @return a list of converted JSON strings
     * @throws com.namaraka.recon.exceptiontype.MyCustomException
     */
    public static <T> List<T> convertFromJson(List<String> stringArrayToConvert, Type objectType) {

        Gson gson = new Gson();

        List list = new ArrayList<>();

        try {
            for (String strToConvert : stringArrayToConvert) {

                list.add(gson.fromJson(strToConvert.trim(), objectType));
            }
        } catch (JsonSyntaxException jse) {
            logger.error("JSON Syntax Error while converting from JSON: " + jse.getMessage());
            //throw new MyCustomException("JSON Syntax Error", ErrorCode.INTERNAL_ERR, "Json syntax error converting from JSON: " + jse.getMessage(), ErrorCategory.SERVER_ERR_TYPE);

        }
        return list;
    }

    /**
     * Return Object from JSON string
     *
     * @param <T>
     * @param stringToConvert
     * @param objectType
     * @return
     */
    public static <T> T convertFromJson(String stringToConvert, Type objectType) throws JsonSyntaxException {

        Gson gson = new Gson();
        return gson.fromJson(stringToConvert.trim(), objectType);
    }

    /**
     * Get the method name generatedActivationCode with key "method" if Json
     * request or enclosing method root name if xml request
     *
     * @param jsonRequest
     * @param apiType
     * @return
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws IOException
     */
    public static String getMethodName(String jsonRequest, APIContentType apiType) throws JsonProcessingException, IOException {

        String methodName = "";

        switch (apiType) {

            case JSON:

                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonRequest);
                methodName = rootNode.path(NamedConstants.JSON_METHOD_NODENAME).asText();

                break;

            case XML:
                break;

        }

        //APIMethodName methodNameEnum = APIMethodName.convertToEnum(methodName);
        return methodName;
    }

    public static void printRequesterHeaderInfo(HttpServletRequest req) throws IOException {

        Enumeration<String> headerNames = req.getHeaderNames();

        while (headerNames.hasMoreElements()) {

            String headerName = headerNames.nextElement();
            logger.debug(">>> header name  : " + headerName);

            Enumeration<String> headers = req.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                logger.debug(">>> header value : " + headerValue);
            }
            logger.debug(">>> -------------------------------------");
        }
    }

    public static void logRequestInfo(HttpServletRequest request) {

        logger.debug(">>> Request Content-type   : " + request.getContentType());
        logger.debug(">>> Request Context-path   : " + request.getContextPath());
        logger.debug(">>> Request Content-type   : " + request.getContentType());
        logger.debug(">>> Request Content-length : " + request.getContentLength());
        logger.debug(">>> Request Protocol       : " + request.getProtocol());
        logger.debug(">>> Request PathInfo       : " + request.getPathInfo());
        logger.debug(">>> Request Remote Address : " + request.getRemoteAddr());
        logger.debug(">>> Request Remote Port    : " + request.getRemotePort());
        logger.debug(">>> Request Server name    : " + request.getServerName());
        logger.debug(">>> Request Querystring    : " + request.getQueryString());
        logger.debug(">>> Request URL            : " + request.getRequestURL().toString());
        logger.debug(">>> Request URI            : " + request.getRequestURI());
    }

    /**
     * Send Json string with a JSONObject parameter
     *
     * @param url
     * @param obj
     * @return
     */
    public static Object sendJsonRequest(final String url, final JSONObject obj) {

        //String myString = new JSONStringer().object().key("JSON").generatedActivationCode("Hello, World!").endObject().toString();
        //produces {"JSON":"Hello, World!"}
        //about to convert the  BEEP API request (in whatever form it is now) -> to a JSON string
        CloseableHttpClient httpClient = null;
        try {
            HttpPost request = new HttpPost(url);
            JSONStringer json = new JSONStringer();
            StringBuilder sb = new StringBuilder();

            if (obj != null) {
                Iterator<String> itKeys = obj.keys();
                if (itKeys.hasNext()) {
                    json.object();
                }
                while (itKeys.hasNext()) {
                    String k = itKeys.next();
                    json.key(k).value(obj.get(k));
                }
            }
            json.endObject();

            StringEntity entity = new StringEntity(json.toString());
            entity.setContentType("application/json;charset=UTF-8");
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
            request.setHeader("Accept", "application/json");
            request.setEntity(entity);

            HttpResponse response;
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient = HttpClientBuilder.create().build();

            response = httpClient.execute(request);

            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (IOException ex) {
            logger.error("IOException: " + ex.getMessage());
            return null;

        } catch (IllegalStateException ex) {
            logger.error("IllegalStateException: " + ex.getMessage());
            return null;

        } catch (JSONException ex) {
            logger.error("JSONException: " + ex.getMessage());
            return null;

        } finally {

            if (httpClient != null) {

                try {
                    httpClient.close();
                } catch (IOException ex) {
                    logger.error("IOException while closing httpClient: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Send Json string with a String parameter
     *
     * @param url
     * @param jsonString
     * @return
     */
    public static Object sendJsonRequest(final String url, final String jsonString) {

        //String myString = new JSONStringer().object().key("JSON").generatedActivationCode("Hello, World!").endObject().toString();
        //produces {"JSON":"Hello, World!"}
        //about to convert the  BEEP API request (in whatever form it is now) -> to a JSON string
        CloseableHttpClient httpClient = null;

        try {
            HttpPost request = new HttpPost(url);
            JSONStringer json = new JSONStringer();
            StringBuilder sb = new StringBuilder();

            StringEntity entity = new StringEntity(jsonString);
            entity.setContentType("application/json;charset=UTF-8");
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
            request.setHeader("Accept", "application/json");
            request.setEntity(entity);

            HttpResponse response;
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient = HttpClientBuilder.create().build();

            response = httpClient.execute(request);

            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (IOException ex) {
            logger.error("IOException: " + ex.getMessage());
            return null;
        } catch (IllegalStateException ex) {
            logger.error("IllegalStateException: " + ex.getMessage());
            return null;
        } catch (JSONException ex) {
            logger.error("JSONException: " + ex.getMessage());
            return null;
        } finally {

            if (httpClient != null) {

                try {
                    httpClient.close();
                } catch (IOException ex) {
                    logger.error("IOException while closing httpClient: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Generate random 5 digit number
     *
     * @return
     */
    public static int generate5Digits() {

        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
    }

    /**
     * Generate short UUID (13 characters)
     *
     * @return short randomValue
     */
    public static String generateShorterRandomID() {

        UUID uuid = UUID.randomUUID();
        //long longValue = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        //randomValue = Long.toString(longValue, Character.MAX_RADIX);
        long lessSignificantBits = uuid.getLeastSignificantBits();
        String randomValue = Long.toString(lessSignificantBits, Character.MAX_RADIX);

        return randomValue.toUpperCase();

    }

    /**
     *
     * @return full randomValue
     */
    public static String generateFullRandomID() {

        UUID uuid = UUID.randomUUID();

        long longValue = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        String randomValue = Long.toString(longValue, Character.MAX_RADIX);

        return randomValue.toUpperCase();
    }

    private static int createRandomInteger(int aStart, long aEnd, Random aRandom) {

        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = aEnd - aStart + 1;
        //logger.info("range>>>>>>>>>>>" + range);
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * aRandom.nextDouble());
        //logger.info("fraction>>>>>>>>>>>>>>>>>>>>" + fraction);
        long randomNumber = fraction + (long) aStart;
        //logger.info("Generated : " + randomNumber);

        return (int) randomNumber;

    }

    /**
     * Generate Activation Codes
     *
     * @param numberOfValuesToGenerate
     * @param telesolaAccount
     * @param generatorId
     * @return
     */
    public static synchronized Set<Integer> generateActivationCodes(int numberOfValuesToGenerate, String telesolaAccount, String generatorId) {

        Set<ActivationCodeTracker> activationCodeTrackers = new HashSet<>();

        List<Integer> activationCodeList = DBManager.fetchOnlyColumn(ActivationCodeTracker.class, "activationCode");

        logger.debug("Fetched from activations_tracker table total: " + activationCodeList.size());

        Set<Integer> activationCodeSet = convertListToSet(activationCodeList);

        int START = 10000000;
        //int END = Integer.parseInt("9999999999");
        //long END = Integer.parseInt("9999999999");
        long END = 99999999L;

        ActivationCodeTracker activationCodeTracker;

        Random random = new Random();

        Set<Integer> newActivationCodes = new HashSet<>(numberOfValuesToGenerate);
        newActivationCodes.clear();
        Set<Integer> trackDuplicates = new HashSet<>();

        Integer generatedActivationCode;

        int recordsAdded = newActivationCodes.size();

        while (recordsAdded < numberOfValuesToGenerate) {

            generatedActivationCode = createRandomInteger(START, END, random);

            //check if this Id is not already generated and stored in the ActivationCodeTracker Table in DB
            if (activationCodeSet.contains(generatedActivationCode)) {

                logger.debug("activationCode generated: " + generatedActivationCode + ", already exists in DB, continuing!!");

            } else {

                boolean anotherIsDuplicate = trackDuplicates.contains(generatedActivationCode);

                if (!anotherIsDuplicate) {

                    logger.debug("NOT Duplicate: " + generatedActivationCode);

                    activationCodeTracker = new ActivationCodeTracker();
                    activationCodeTracker.setGeneratorId(generatorId);
                    activationCodeTracker.setActivationCode(generatedActivationCode);
                    activationCodeTracker.setTelesolaAccount(telesolaAccount);

                    activationCodeTrackers.add(activationCodeTracker);
                    newActivationCodes.add(generatedActivationCode);

                    trackDuplicates.add(generatedActivationCode);

                    recordsAdded++;

                } else {
                    logger.debug("IS Duplicate code: " + generatedActivationCode);
                }

            }

        }

        //insert Database with the new IDs
        DBManager.bulkSave(activationCodeTrackers);

        /*for (int idx = 1; idx <= numberOfValuesToGenerate; ++idx) {
            createRandomInteger(START, END, random);
        }*/
        return newActivationCodes;
    }

    /**
     *
     * @param telesolaAccount
     * @param generatorId
     * @return
     */
    public static synchronized int generateOTP(String telesolaAccount, String generatorId) {

        int START = 10000;
        long END = 99999L;

        Random random = new Random();
        int generatedOTP = createRandomInteger(START, END, random);

        OneTimePin otp = new OneTimePin();
        otp.setGeneratorId(generatorId);
        otp.setTelesolaAccount(telesolaAccount);
        otp.setOtp(generatedOTP);
        otp.setIsOTPValid(Boolean.TRUE);

        long id = DBManager.persistDatabaseModel(otp);

        return generatedOTP;
    }

    /**
     *
     * @param stringToConvert
     * @return
     * @throws ParseException
     */
    public static BigDecimal convertStringToBigDecimal(String stringToConvert) throws ParseException {

        // Create a DecimalFormat that fits your requirements
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        // parse the string
        BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(stringToConvert);

        return bigDecimal;

    }

    /**
     *
     * @param amount
     * @return
     */
    public static Amounttype getAmountTypeForBigDecimal(String amount) {

        Amounttype amountType = new Amounttype();
        BigDecimal decimalAmount = BigDecimal.ZERO;
        try {
            decimalAmount = GeneralUtils.convertStringToBigDecimal(amount);
        } catch (ParseException ex) {
            logger.error("Failed to convert string amount: " + amount + " to BigDecimal");
        }
        //amountType.setAmount(decimalAmount);
        //amountType.setCurrencycode("UGX"); //we will be picking this from the API once updated on app end    

        return amountType;
    }

    /**
     * Get Amounttpe from String
     *
     * @param amount
     * @return
     */
    public static Amounttype getAmountType(String amount) {

        Amounttype amountType = new Amounttype();

        int amountInt = Integer.parseInt(amount);
        amountInt = roundUpToNext100(amountInt);

        amountType.setAmount(amountInt);
        amountType.setCurrencycode("UGX"); //we will be picking this from the API once updated on app end    

        return amountType;
    }

    /**
     * Get Amounttpe from int
     *
     * @param amount
     * @return
     */
    public static Amounttype getAmountType(int amount) {

        Amounttype amountType = new Amounttype();

        amountType.setAmount(amount);
        amountType.setCurrencycode("UGX"); //we will be picking this from the API once updated on app end    

        return amountType;
    }

    public static List<NameValuePair> convertToNameValuePair(Map<String, Object> pairs) {

        if (pairs == null) {
            return null;
        }

        List<NameValuePair> nvpList = new ArrayList<>(pairs.size());

        for (Map.Entry<String, Object> entry : pairs.entrySet()) {
            nvpList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }

        return nvpList;

    }

    /**
     * formatMSISDN
     *
     * @param MSISDN
     * @return
     */
    public static String formatMSISDN(String MSISDN) {

        if (MSISDN.startsWith("+")) {
            MSISDN = MSISDN.replace("+", "").trim();
        }
        Long phoneNumber;
        try {
            phoneNumber = Long.valueOf(MSISDN);
        } catch (NumberFormatException ex) {
            logger.error("Could not convert number to a Long value: " + ex.getMessage() + ". So returning the number as it was.");
            return MSISDN;
        }
        int length = phoneNumber.toString().length();

        switch (length) {
            case 12:
                logger.info("MSISDN [ "
                        + MSISDN + "] has length: " + MSISDN.length()
                        + " when converted to a long value. No fix to be done");
                break;
            case 9:
                logger.info("MSISDN [ "
                        + MSISDN + "] has length: " + MSISDN.length() + ". "
                        + " when converted to a long value."
                        + " An attempt to fix the number by adding a prefix "
                        + "will be done");
                if (phoneNumber.toString().startsWith("7")) {
                    MSISDN = 256 + phoneNumber.toString();
                }
                break;

            default:
                logger.info("MSISDN [ "
                        + MSISDN + "] has length " + MSISDN.length()
                        + " when converted to a long value. "
                        + "Will be sent as is.");
                break;
        }
        
        logger.debug("Returning formatted MSISDN as: " + MSISDN);
        
        return MSISDN;
    }

    /**
     * Generate a Telesola account from the client's primaryContact and
     * clientType
     *
     * @param primaryContact
     * @param clientType
     * @return
     */
    public static String generateTelesolaAccount(String primaryContact, ClientType clientType) {
        String shortContact = primaryContact.substring(3);
        return (clientType.getValue() + shortContact);

        //To-Do
        //Separate accounts by region, especially for distributors e.g. DKLA774983602 for a Kampala Distributor
    }

    public String generateNextIdAfterThis(String idNumToIncrement) {
        Pattern compile = Pattern.compile("^(.*?)([0-9]*|[A-Z]*)$");
        Matcher matcher = compile.matcher(idNumToIncrement);
        String remaining = idNumToIncrement;
        String currentGroup = "";
        String result = "";

        boolean continueToNext = true;
        while (matcher.matches() && continueToNext) {
            remaining = matcher.group(1);
            currentGroup = matcher.group(2);
            int currentGroupLength = currentGroup.length();
            int base = currentGroup.matches("[0-9]*") ? 10 : 36;
            currentGroup = Long.toString(Long.parseLong("1" + currentGroup, base) + 1, base);  // The "1" if just to ensure that "000" doesn't become 0 (and thus losing the original string length)
            currentGroup = currentGroup.substring(currentGroup.length() - currentGroupLength, currentGroup.length());
            continueToNext = Long.valueOf(currentGroup, base) == 0;
            if (base == 36) {
                currentGroup = currentGroup.replace("0", "A");
            }

            result = currentGroup + result;
            matcher = compile.matcher(remaining);
        }

        result = remaining + result;
        return result.toUpperCase();
    }

    public String generateNextIdAfterThis2(String idNumToIncrement) {

        Pattern compile = Pattern.compile("^(.*?)([9Z]*)$");
        Matcher matcher = compile.matcher(idNumToIncrement);
        String left = "";
        String right = "";
        if (matcher.matches()) {
            left = matcher.group(1);
            right = matcher.group(2);
        }
        idNumToIncrement = !left.isEmpty() ? Long.toString(Long.parseLong(left, 36) + 1, 36) : "";
        idNumToIncrement += right.replace("Z", "A").replace("9", "0");
        return idNumToIncrement.toUpperCase();

    }

    public String generateNextIdAfterThis3(String idNumToIncrement) {

        char[] cars = idNumToIncrement.toUpperCase().toCharArray();
        OUTER:
        for (int i = cars.length - 1; i >= 0; i--) {
            switch (cars[i]) {
                case 'Z':
                    cars[i] = 'A';
                    break;
                case '9':
                    cars[i] = '0';
                    break;
                default:
                    cars[i]++;
                    break OUTER;
            }
        }
        return String.valueOf(cars);
    }

    /**
     *
     * @param commercialStatus
     * @param frequency
     * @return
     */
    public static int getEnableDuration(CommercialStatus commercialStatus, InstallmentFrequency frequency) {

        int duration = 7;

        if (commercialStatus == CommercialStatus.INSTALLMENT) {

            // For installments, let's set it to zero till confirmation of payment of the deposit 
            //or the generator Unit in full
            switch (frequency) {

                case WEEKLY:
                    duration = 7;
                    //duration = 0;
                    break;

                case BIWEEKLY:
                    duration = 15;
                    //duration = 0;
                    break;

                case MONTHLY:
                    duration = 30;
                    //duration = 0;
                    break;

                default:
                    duration = 7;
                    //duration = 0;
                    break;

            }
        } else if (commercialStatus == CommercialStatus.SOLD) {

        }
        return duration;
    }

    /**
     * Convert Set to List
     *
     * @param <T>
     * @param set
     * @return
     */
    public static <T> List<T> convertSetToList(Set<T> set) {

        List<T> newList = new ArrayList<>(set);
        return newList;
    }

    /**
     * Convert List to Set
     *
     * @param <T>
     * @param list
     * @return
     */
    public static <T> Set<T> convertListToSet(List<T> list) {

        System.out.println("1st : " + MapFormat.format("", new HashMap<>()));

        Set<T> set = new HashSet<>(list);
        return set;
    }

    /*public String getActivationCodeMessage(String lastName, String firstName, String phoneNumber) {
            
        String templateMessage = IOUtils.readLines(this.getClass().getResourceAsStream("registration.stl"));
        
        ST template = new ST(templateMessage);
        Map params = new HashMap();
        params.put("phoneNumber", phoneNumber);
        params.put("lastName", lastName);
        params.put("firstName", firstName);
        template.setAttributes(params);
        return template.toString();
    }*/
    /**
     * getActivationMessage from template
     *
     * @param firstName
     * @param amount
     * @param outstandingBalance
     * @param activationCode
     * @param numberOfActiveDays
     * @return
     */
    public static String getActivationCodeMessage(String firstName, int amount, int outstandingBalance, String activationCode, int numberOfActiveDays) {

        //Object[] params = {"nameRobert", "rhume55@gmail.com"};
        Map<String, Object> map = new HashMap<>();

        map.put("firstName", firstName);
        map.put("amount", addCommasAndCurrency(amount));
        map.put("outstandingBalance", addCommasAndCurrency(outstandingBalance));
        map.put("activationCode", activationCode);
        map.put("numberOfActiveDays", String.valueOf(numberOfActiveDays));

        String message = MapFormat.format(NamedConstants.SMS_TEMPLATE_ACT_CODE, map);
        logger.debug("Activation message going out : " + message);

        return message;
    }

    /**
     * getPaymentFailMessage message from Template
     *
     * @param firstName
     * @param amount
     * @param generatorId
     * @param statusDescription
     * @return
     */
    public static String getPaymentFailMessage(String firstName, int amount, String generatorId, String statusDescription) {

        //Object[] params = {"nameRobert", "rhume55@gmail.com"};
        Map<String, Object> map = new HashMap<>();

        map.put("firstName", firstName);
        map.put("amount", addCommasAndCurrency(amount));
        map.put("generatorId", generatorId);
        map.put("statusDescription", statusDescription);

        String message = MapFormat.format(NamedConstants.SMS_PAYMENT_FAILURE, map);
        logger.debug("Payment Failure message going out : " + message);

        return message;
    }

    /**
     * getActivationMessage from template
     *
     * @param firstName
     * @param otp
     * @param telesolaAccount
     * @param generatorId
     * @return
     */
    public static String getOTPMessage(String firstName, String otp, String telesolaAccount, String generatorId) {

        //Object[] params = {"nameRobert", "rhume55@gmail.com"};
        Map<String, String> map = new HashMap<>();

        map.put("firstName", firstName);
        map.put("otp", otp);
        //map.put("telesolaAccount", telesolaAccount);
        map.put("generatorId", generatorId);

        String message = MapFormat.format(NamedConstants.SMS_TEMPLATE_OTP, map);
        logger.debug("OTP message : " + message);

        return message;
    }

    /**
     *
     * @param smsText
     * @param recipientNumber
     * @return
     */
    public static Map<String, Object> prepareTextMsgParams(String smsText, String recipientNumber) {

        Map<String, Object> paramPairs = new HashMap<>();

        paramPairs.put(NamedConstants.SMS_API_PARAM_USERNAME, NamedConstants.SMS_API_USERNAME);
        paramPairs.put(NamedConstants.SMS_API_PARAM_PASSOWRD, NamedConstants.SMS_API_PASSWORD);
        paramPairs.put(NamedConstants.SMS_API_PARAM_SENDER, NamedConstants.SMS_API_SENDER_NAME);
        paramPairs.put(NamedConstants.SMS_API_PARAM_TEXT, smsText);
        paramPairs.put(NamedConstants.SMS_API_PARAM_RECIPIENT, recipientNumber);

        return paramPairs;
    }

    /**
     * Round up to next 100th integer
     *
     * @param value
     * @return
     */
    public static int roundUpToNext100(double value) {

        return (int) (Math.ceil(value / 100.0) * 100);
    }

    /**
     * Round up to next integer
     *
     * @param value
     * @return
     */
    public static int roundUpToNextInt(double value) {
        return (int) Math.ceil(value);
    }

    /**
     * Add commas to a number
     *
     * @param numberToFormat
     * @return
     */
    public static String addCommas1(int numberToFormat) {

        return (NumberFormat.getNumberInstance(Locale.US).format(numberToFormat));
    }

    /**
     *
     * @param numberToAddCommas
     * @return
     */
    public static String addCommas2(int numberToAddCommas) {

        String str = "UGX" + String.valueOf(numberToAddCommas).replaceAll("/\\B(?=(\\d{3})+(?!\\d))/g", ",");

        logger.debug("Formatted amount string is: " + str);

        return str;
    }

    /**
     * Add (a) comma(s) to a number
     *
     * @param numberToAddCommas
     * @return
     */
    public static String addCommasAndCurrency(int numberToAddCommas) {

        DecimalFormat myFormatter = new DecimalFormat("#,###");
        String output = "UGX" + myFormatter.format(numberToAddCommas);

        logger.debug("Formatted amount string is: " + output);

        return output;

    }

    /**
     * Send out SMS
     *
     * @param paramPairs
     * @return
     */
    public static String sendSMS(Map<String, Object> paramPairs) {

        //String response = "Assume an SMS is sent and this is the response, hihihihi, LOLEST!!";
        String response = AppEntry.clientPool.sendRemoteRequest("", NamedConstants.SMS_API_URL, paramPairs, HTTPMethod.GET);

        return response;
    }
}
