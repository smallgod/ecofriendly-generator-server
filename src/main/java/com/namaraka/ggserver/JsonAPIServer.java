/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver;

import com.namaraka.ggserver.constant.APIContentType;
import com.namaraka.ggserver.constant.APIMethodName;
import com.namaraka.ggserver.constant.StatusType;
import com.namaraka.ggserver.jsondata.MakePaymentRequest;
import com.namaraka.ggserver.jsondata.MakePaymentResponse;
import com.namaraka.ggserver.jsondata.PaymentStatusCallBackRequest;
import com.namaraka.ggserver.jsondata.PaymentStatusCallBackResponse;
import com.namaraka.ggserver.utils.GeneralUtils;
import static com.namaraka.ggserver.utils.GeneralUtils.getMethodName;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.writeResponse;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;

/**
 *
 * @author smallgod
 */
@WebServlet(asyncSupported = true)
public class JsonAPIServer extends HttpServlet {

    private static final long serialVersionUID = 7200688391501280131L;

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

                jsonResponse = makePayment(jsonRequest);
                break;

            case PAYMENT_STATUS:
                break;
                
            case PAYMENT_STATUS_CALLBACK:
                jsonResponse = paymentStatusCallBackResponse(jsonRequest);
                break;

            default:
                break;

        }
        
        System.out.println("---------- Response --------\n" + GeneralUtils.toPrettyJsonFormat(jsonResponse));
        writeResponse(response, jsonResponse);

    }
    
    
    String makePayment(String paymentJsonRequest){
        
        //steps to implement
        //1. Log payment to DB
        //2. respond with LOGGED if ok to client otherwise NOT_LOGGED
        //3. 
        
        
        MakePaymentRequest paymentRequest = convertFromJson(paymentJsonRequest, MakePaymentRequest.class);
        
        MakePaymentResponse paymentResponse = new MakePaymentResponse();
        
        String userId = paymentRequest.getParams().getUserId();
        String status = StatusType.LOGGED.getValue();
        String invoiceNumber = paymentRequest.getParams().getInvoiceNumber();
        String appKey = paymentRequest.getParams().getAppKey();
        String creationDate = paymentRequest.getParams().getCreationDate();
        String cmsPaymentID = "generate it";
        String amount = paymentRequest.getParams().getAmount();
        String debitAccount = paymentRequest.getParams().getDebitAccount();
        
        //response
        paymentResponse.setUserId(userId);
        paymentResponse.setInvoiceNumber(invoiceNumber);
        paymentResponse.setCmsPaymentId(cmsPaymentID);
        paymentResponse.setStatus(status);
        paymentResponse.setStatusDescription("Payment Logged Successfully");
        
        String resp = GeneralUtils.convertToJson(paymentResponse, MakePaymentResponse.class);

        return resp;
        

    }
    
    
    String paymentStatusCallBackResponse(String paymentStatusJsonRequest){
        
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
        statusCallbackResponse.setAcknowledgeStatus(StatusType.SUCCESSFUL.getValue());
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
