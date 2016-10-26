/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver;

import com.namaraka.ggserver.constant.APIContentType;
import com.namaraka.ggserver.constant.APIMethodName;
import com.namaraka.ggserver.constant.NamedConstants;
import com.namaraka.ggserver.constant.Status;
import com.namaraka.ggserver.dbaccess.DBManager;
import com.namaraka.ggserver.jsondata.MakePaymentRequest;
import com.namaraka.ggserver.jsondata.MakePaymentResponse;
import com.namaraka.ggserver.jsondata.PaymentStatusCallBackRequest;
import com.namaraka.ggserver.jsondata.PaymentStatusCallBackResponse;
import com.namaraka.ggserver.model.v1_0.Amounttype;
import com.namaraka.ggserver.model.v1_0.MoMoTransaction;
import com.namaraka.ggserver.utils.DateUtils;
import com.namaraka.ggserver.utils.GeneralUtils;
import static com.namaraka.ggserver.utils.GeneralUtils.getMethodName;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.namaraka.ggserver.utils.GeneralUtils.writeResponse;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import com.namaraka.ggserver.utils.JodaDateTimeConverter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import org.joda.time.DateTime;

/**
 *
 * @author smallgod
 */
@WebServlet(asyncSupported = true)
public class JsonAPIServer extends HttpServlet {

    private static final long serialVersionUID = 7200688391501280131L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JsonAPIServer.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if sa servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        System.out.println("API Servlet called!!!");

        //logRequestInfo(request);
        //printRequesterHeaderInfo(request);
        final String jsonRequest = GeneralUtils.getJsonStringFromRequest(request);

        System.out.println("---------- Request --------\n" + GeneralUtils.toPrettyJsonFormat(jsonRequest));

        String methodName = getMethodName(jsonRequest, APIContentType.JSON);
        APIMethodName methodNameEnum = APIMethodName.convertToEnum(methodName);

        String jsonResponse = "";

        switch (methodNameEnum) {

            case MAKE_PAYMENT:

                System.out.println("MAKE_PAYMENT called");
                jsonResponse = makePayment(jsonRequest);
                break;

            case PAYMENT_STATUS:
                System.out.println("PAYMENT_STATUS called");
                jsonResponse = "{\"telesola_account\":\"0774983602\",\"generator_id\":\"A001\",\"cms_payment_id\":\"794001\",\"momo_id\":\"23577744357\",\"payment_date\":\"2016-07-25 08:55:09\",\"enable_duration\":\"7\",\"activation_code\":\"5784\",\"status\":\"SUCCESSFUL\",\"description\":\"Processing processed successfully\"}";
                break;

            case PAYMENT_HISTORY:
                System.out.println("PAYMENT_HISTORY called");
                jsonResponse = "{\"telesola_account\":\"786577309\",\"units\":[{\"generator_id\":\"A001\",\"cms_payment_id\":\"3509866\",\"momo_id\":\"893783739\",\"momo_account\":\"256783937043\",\"amount\":\"59000\",\"payment_date\":\"2016-09-28 08:55:09\",\"acknowledge_date\":\"2016-09-28 08:55:09\"},{\"generator_id\":\"A002\",\"cms_payment_id\":\"61866\",\"momo_id\":\"893783669\",\"momo_account\":\"256783937043\",\"amount\":\"58000\",\"payment_date\":\"2016-07-28 08:55:09\",\"acknowledge_date\":\"2016-09-28 08:55:09\"}]}";
                break;
            case ACCOUNT_SETUP:
                System.out.println("ACCOUNT_SETUP called");
                jsonResponse = "{\"telesola_account\":\"786577309\",\"units\":[{\"generator_id\":\"A001\",\"mac_address\":\"345S35WET55YH\",\"commercial_status\":\"INSTALLMENT\",\"contract_date\":\"2016-09-28 08:55:09\",\"distributor_id\":\"KLA44009\",\"distributor_key\":\"561277\",\"contracted_price\":\"5450000\",\"installment_frequency\":\"WEEKLY\",\"enable_duration\":\"7\",\"installment_day\":\"1\",\"momo_account\":\"256774985275\",\"outstanding_balance\":\"550900\",\"activation_codes\":[757853,69434]},{\"generator_id\":\"A002\",\"mac_address\":\"345S35T2WSH\",\"commercial_status\":\"INSTALLMENT\",\"contract_date\":\"2016-04-25 08:55:09\",\"distributor_id\":\"KLA84019\",\"distributor_key\":\"5644277\",\"contracted_price\":\"545000\",\"installment_frequency\":\"MONTHLY\",\"enable_duration\":\"30\",\"installment_day\":\"4\",\"momo_account\":\"256774985275\",\"outstanding_balance\":\"30900\",\"activation_codes\":[757258,343222,68484]}]}";
                break;
            case ACTIVATION_CODES:
                System.out.println("ACTIVATION_CODES called");
                jsonResponse = "{\"telesola_account\":\"778789543\",\"generator_id\":\"A001\",\"activation_codes\":[757858,694844,84647,68484,5859484,58555]}";
                break;

            case REGISTER_CUSTOMER:
                System.out.println("REGISTER_CUSTOMER called");
                jsonResponse = "{\"telesola_account\":\"774983602\",\"status\":\"LOGGED\",\"description\":\"New customer successfully registered\"}";
                break;
                
            case REGISTER_UNIT:
                System.out.println("REGISTER_UNIT called");
                jsonResponse = "{\"telesola_account\":\"774987643\",\"generator_id\":\"A001\",\"status\":\"SUCCESSFUL\",\"description\":\"Unit registered successfuly\"}";
                break;
                
            case REQUEST_OTP:
                System.out.println("REQUEST_OTP called");
                jsonResponse = "{\"telesola_account\":\"774987643\",\"primary_phone\":\"256774568901\",\"status\":\"SUCCESSFUL\",\"description\":\"One-time PIN sent to 256774568901 successfuly\"}";
                break;
                
            case SYNC_UNIT:
                System.out.println("SYNC_UNIT called");
                jsonResponse = "";
                break;
                
            case PAYMENT_STATUS_CALLBACK:
                System.out.println("PAYMENT_STATUS_CALLBACK called");
                jsonResponse = paymentStatusCallBackResponse(jsonRequest);
                break;

            default:
                break;

        }

        System.out.println("---------- Response --------\n" + GeneralUtils.toPrettyJsonFormat(jsonResponse));
        writeResponse(response, jsonResponse);

    }

    String makePayment(String paymentJsonRequest) {

        //steps to implement
        //1. Log payment to DB
        //2. respond with LOGGED if ok to client otherwise NOT_LOGGED
        //3. Notify fetcher to send to aggregator via a call back i think
        MakePaymentRequest paymentRequest = convertFromJson(paymentJsonRequest, MakePaymentRequest.class);

        MakePaymentResponse paymentResponse = new MakePaymentResponse();

        String userId = paymentRequest.getParams().getUserId();
        String status = Status.LOGGED.getValue();
        String statusDescription = "Payment logged for processing";
        String invoiceNumber = paymentRequest.getParams().getInvoiceNumber();
        String appKey = paymentRequest.getParams().getAppKey();
        String creationDate = paymentRequest.getParams().getCreationDate();
        String cmsTransactionID = GeneralUtils.generateShorterRandomID();
        String amount = paymentRequest.getParams().getAmount();
        //String currency = paymentRequest.getParams().getCurrency();
        //String payerNarration = paymentRequest.getParams().getPayerNarration();
        String debitAccount = paymentRequest.getParams().getDebitAccount();

        //persist to DB
        Amounttype amountType = new Amounttype();
        BigDecimal decimalAmount = BigDecimal.ZERO;
        try {
            decimalAmount = GeneralUtils.convertStringToBigDecimal(amount);
        } catch (ParseException ex) {
            logger.error("Failed to convert string amount: " + amount + " to BigDecimal");
        }
        amountType.setAmount(decimalAmount);
        amountType.setCurrencycode("UGX"); //we will picking this from the API once updated on app end    

        MoMoTransaction transaction = new MoMoTransaction();
        transaction.setCustomerMsisdn(userId);
        //transaction.setCreationDate(DateUtils.convertStringToDate(creationDate, NamedConstants.DATE_TIME_FORMAT));
        transaction.setInvoiceNumber(invoiceNumber);
        transaction.setCmsTransactionID(cmsTransactionID);
        transaction.setAmount(amountType);
        transaction.setPayerNarration("My installment payment");
        transaction.setDebitAccount(debitAccount);

        long id = DBManager.persistDatabaseModel(transaction);

        if (id == -1L) {
            status = Status.NOT_LOGGED.getValue();
            statusDescription = "Failed to log payment in the database";

        }

        //response
        paymentResponse.setUserId(userId);
        paymentResponse.setInvoiceNumber(invoiceNumber);
        paymentResponse.setCmsPaymentId(cmsTransactionID);
        paymentResponse.setStatus(status);
        paymentResponse.setStatusDescription(statusDescription);

        String resp = GeneralUtils.convertToJson(paymentResponse, MakePaymentResponse.class);

        return resp;

    }

    String paymentStatusCallBackResponse(String paymentStatusJsonRequest) {

        //steps to implement
        //1. Log payment to DB
        //2. respond with LOGGED if ok to client otherwise NOT_LOGGED
        //3. 
        PaymentStatusCallBackRequest statusCallbackRequest = convertFromJson(paymentStatusJsonRequest, PaymentStatusCallBackRequest.class);

        PaymentStatusCallBackResponse statusCallbackResponse = new PaymentStatusCallBackResponse();

        String reference = statusCallbackRequest.getParams().getReference();
        String transactionId = statusCallbackRequest.getParams().getTransactionId();
        String momoId = statusCallbackRequest.getParams().getMomoId();
        String subscriptionId = statusCallbackRequest.getParams().getSubscriptionId();
        String approvalDate = statusCallbackRequest.getParams().getApprovalDate();
        String statusMessage = statusCallbackRequest.getParams().getStatusMessage();
        String statusCode = statusCallbackRequest.getParams().getStatusCode();

        //response
        statusCallbackResponse.setReference(reference);
        statusCallbackResponse.setTransactionId(transactionId);
        statusCallbackResponse.setAcknowledgeStatus(Status.SUCCESSFUL.getValue());
        statusCallbackResponse.setAcknowledgeDescription("Status acknowledged");

        String resp = GeneralUtils.convertToJson(statusCallbackResponse, PaymentStatusCallBackResponse.class);

        return resp;

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * The init method is designed to be called only once. It is called when the
     * servlet is first created, and not called again for each user request. So,
     * it is used for one-time initializations, just as with the init method of
     * applets. The servlet is normally created when a user first invokes a URL
     * corresponding to the servlet, but you can also specify that the servlet
     * be loaded when the server is first started. When a user invokes a
     * servlet, a single instance of each servlet gets created, with each user
     * request resulting in a new thread that is handed off to doGet or doPost
     * as appropriate. The init() method simply creates or loads some data that
     * will be used throughout the life of the servlet.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {

    }

    /**
     * The destroy() method is called only once at the end of the life cycle of
     * a servlet. This method gives your servlet a chance to close database
     * connections, halt background threads, write cookie lists or hit counts to
     * disk, and perform other such cleanup activities. After the destroy()
     * method is called, the servlet object is marked for garbage collection
     */
    @Override
    public void destroy() {
        // Finalization code...
    }

}
