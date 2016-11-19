/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver;

import com.namaraka.ggserver.constant.APIContentType;
import com.namaraka.ggserver.constant.APIMethodName;
import com.namaraka.ggserver.constant.ClientStatus;
import com.namaraka.ggserver.constant.ClientType;
import com.namaraka.ggserver.constant.CommercialStatus;
import com.namaraka.ggserver.constant.HTTPMethod;
import com.namaraka.ggserver.constant.InstallmentDay;
import com.namaraka.ggserver.constant.InstallmentFrequency;
import com.namaraka.ggserver.constant.NamedConstants;
import com.namaraka.ggserver.constant.OrderFirst;
import com.namaraka.ggserver.constant.PaymentProgress;
import com.namaraka.ggserver.constant.Status;
import com.namaraka.ggserver.dbaccess.DBManager;
import com.namaraka.ggserver.jsondata.AccountSetupRequest;
import com.namaraka.ggserver.jsondata.AccountSetupResponse;
import com.namaraka.ggserver.jsondata.ClientRegistrationRequest;
import com.namaraka.ggserver.jsondata.ClientRegistrationResponse;
import com.namaraka.ggserver.jsondata.MakePaymentRequest;
import com.namaraka.ggserver.jsondata.MakePaymentResponse;
import com.namaraka.ggserver.jsondata.PaymentHistoryRequest;
import com.namaraka.ggserver.jsondata.PaymentHistoryResponse;
import com.namaraka.ggserver.jsondata.ActivationCodes;
import com.namaraka.ggserver.jsondata.ReportPaymentsResponse;
import com.namaraka.ggserver.jsondata.PaymentStatusCallBackRequest;
import com.namaraka.ggserver.jsondata.PaymentStatusCallBackResponse;
import com.namaraka.ggserver.jsondata.ReportPaymentsRequest;
import com.namaraka.ggserver.jsondata.UnitRegistrationRequest;
import com.namaraka.ggserver.jsondata.UnitRegistrationResponse;
import com.namaraka.ggserver.model.v1_0.Amounttype;
import com.namaraka.ggserver.model.v1_0.Client;
import com.namaraka.ggserver.model.v1_0.GeneratorUnit;
import com.namaraka.ggserver.model.v1_0.MoMoPayment;
import com.namaraka.ggserver.model.v1_0.Name;
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
import java.text.ParseException;
import org.slf4j.LoggerFactory;
import com.namaraka.ggserver.utils.AlphaNumericIDGenerator;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.joda.time.LocalDate;
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

        System.out.println("API Servlet called here!!!");

        response.setContentType("application/json");

        //logRequestInfo(request);
        //printRequesterHeaderInfo(request);
        final String jsonRequest = GeneralUtils.getJsonStringFromRequest(request);

        System.out.println("---------- Request --------\n" + GeneralUtils.toPrettyJsonFormat(jsonRequest));

        String methodName = getMethodName(jsonRequest, APIContentType.JSON);
        APIMethodName methodNameEnum = APIMethodName.convertToEnum(methodName);

        String jsonResponse = "";

        switch (methodNameEnum) {

            case REPORT_PAYMENTS:

                System.out.println("REPORT_PAYMENTS called");
                jsonResponse = reportPayments(jsonRequest);
                break;

            case MAKE_PAYMENT:

                System.out.println("MAKE_PAYMENT called");
                jsonResponse = makePayment(jsonRequest);
                //jsonResponse = "{\"telesola_account\":\"774983602\",\"generator_id\":\"A001\",\"cms_payment_id\":\"5879594\",\"status\":\"LOGGED\",\"description\":\"Payment Successfully Logged for processing\"}";
                break;

            case PAYMENT_STATUS_CALLBACK:
                System.out.println("PAYMENT_STATUS_CALLBACK called");
                jsonResponse = paymentStatusCallBackResponse(jsonRequest);
                break;

            case REGISTER_UNIT:
                System.out.println("REGISTER_UNIT called");
                //jsonResponse = "{\"telesola_account\":\"774987643\",\"generator_id\":\"A001\",\"installment_amount\":\"50000\",\"status\":\"SUCCESSFUL\",\"description\":\"Unit registered successfuly\"}";
                jsonResponse = registerGenerator(jsonRequest);
                break;

            case REGISTER_CLIENT:
                System.out.println("REGISTER_CLIENT called");
                //jsonResponse = "{\"telesola_account\":\"C774983602\",\"status\":\"LOGGED\",\"description\":\"New client successfully registered\"}";
                jsonResponse = registerClient(jsonRequest);
                break;

            case PAYMENT_HISTORY:
                System.out.println("PAYMENT_HISTORY called");
                //jsonResponse = "{\"telesola_account\":\"786577309\",\"dataList\":[{\"generator_id\":\"A001\",\"cms_payment_id\":\"3509866\",\"enable_duration\":\"7\",\"momo_id\":\"893783739\",\"momo_account\":\"256783937043\",\"amount\":\"59000\",\"payment_date\":\"2016-09-28 08:55:09\",\"acknowledge_date\":\"2016-09-28 08:55:09\"},{\"generator_id\":\"A002\",\"cms_payment_id\":\"61866\",\"momo_id\":\"893783669\",\"momo_account\":\"256783937043\",\"amount\":\"58000\",\"payment_date\":\"2016-07-28 08:55:09\",\"acknowledge_date\":\"2016-09-28 08:55:09\"}]}";
                jsonResponse = getPaymentHistory(jsonRequest);
                break;

            case ACCOUNT_SETUP:
                System.out.println("ACCOUNT_SETUP called");
                //jsonResponse = "{\"telesola_account\":\"C786577309\",\"dataList\":[{\"generator_id\":\"A001\",\"mac_address\":\"345S35WET55YH\",\"commercial_status\":\"INSTALLMENT\",\"contract_date\":\"2016-09-28 08:55:09\",\"distributor_id\":\"KLA44009\",\"distributor_key\":\"561277\",\"contracted_price\":\"5450000\",\"installment_frequency\":\"WEEKLY\",\"enable_duration\":\"7\",\"installment_day\":\"1\",\"momo_account\":\"256774985275\",\"outstanding_balance\":\"550900\",\"activation_codes\":[757853,69434]},{\"generator_id\":\"A002\",\"mac_address\":\"345S35T2WSH\",\"commercial_status\":\"INSTALLMENT\",\"contract_date\":\"2016-04-25 08:55:09\",\"distributor_id\":\"KLA84019\",\"distributor_key\":\"5644277\",\"contracted_price\":\"545000\",\"installment_frequency\":\"MONTHLY\",\"enable_duration\":\"30\",\"installment_day\":\"4\",\"momo_account\":\"256774985275\",\"outstanding_balance\":\"30900\",\"activation_codes\":[757258,343222,68484]}]}";
                jsonResponse = accountSetup(jsonRequest);
                break;

            case PAYMENT_STATUS:
                System.out.println("PAYMENT_STATUS called");
                jsonResponse = "{\"telesola_account\":\"0774983602\",\"generator_id\":\"A001\",\"cms_payment_id\":\"794001\",\"momo_id\":\"23577744357\",\"payment_date\":\"2016-07-25 08:55:09\",\"enable_duration\":\"7\",\"activation_code\":\"5784\",\"status\":\"SUCCESSFUL\",\"description\":\"Processing processed successfully\"}";
                break;

            case ACTIVATION_CODES:
                System.out.println("ACTIVATION_CODES called");
                jsonResponse = "{\"telesola_account\":\"C778789543\",\"generator_id\":\"A001\",\"activation_codes\":[757858,694844,84647,68484,5859484,58555]}";
                break;

            case REQUEST_OTP:
                System.out.println("REQUEST_OTP called");
                jsonResponse = "{\"telesola_account\":\"774987643\",\"primary_phone\":\"256774568901\",\"status\":\"SUCCESSFUL\",\"description\":\"One-time PIN sent to 256774568901 successfuly\"}";
                break;

            case SYNC_UNIT:
                System.out.println("SYNC_UNIT called");
                jsonResponse = "";
                break;

            default:
                break;

        }

        System.out.println("---------- Response --------\n" + GeneralUtils.toPrettyJsonFormat(jsonResponse));
        writeResponse(response, jsonResponse);

    }

    String accountSetup(String accountSetupPayload) {

        AccountSetupResponse accountSetupResponse = new AccountSetupResponse();
        List<AccountSetupResponse.Unit> units = new ArrayList<>();

        accountSetupResponse.setUnits(units);

        String status = Status.LOGGED.getValue();
        String statusDescription = "New CLient logged successfully";
        String telesolaAccount = "";

        try {

            AccountSetupRequest accountSetupRequest = convertFromJson(accountSetupPayload, AccountSetupRequest.class);

            if (accountSetupRequest == null) {

                logger.error("AccountSetupRequest is null, failed to un-marshal JSON");

            } else {

                telesolaAccount = accountSetupRequest.getParams().getTelesolaAccount();
                String otp = accountSetupRequest.getParams().getOtp();
                String appKey = accountSetupRequest.getParams().getAppKey();

                //To-Do
                //Compare OTP with OTP that was generated and sent to client
                //mark OTP used
                //select most recent PIN from DB
                //DBManager.fetchSingleRecord(OneTimePin.class, appKey, status)
                Set<GeneratorUnit> generators = DBManager.bulkFetchByPropertyName(GeneratorUnit.class, "telesolaAccount", telesolaAccount);
                Iterator<GeneratorUnit> generatorIter = generators.iterator();

                while (generatorIter.hasNext()) {

                    GeneratorUnit generator = generatorIter.next();

                    AccountSetupResponse.Unit unit = accountSetupResponse.new Unit();
                    unit.setCommercialStatus(generator.getCommercialStatus().getValue());
                    unit.setContractDate(DateUtils.convertLocalDateToString(generator.getContractDate(), NamedConstants.DATE_DASH_FORMAT));
                    unit.setContractPrice(String.valueOf(generator.getContractPrice().getAmount()));
                    unit.setEnableDuration(String.valueOf(generator.getEnableDurationDefault()));
                    unit.setGeneratorId(generator.getGeneratorId());
                    unit.setInstallmentAmount(String.valueOf(generator.getInstallmentAmount().getAmount()));
                    unit.setInstallmentDay(generator.getInstallmentDay().getValue());
                    unit.setInstallmentFrequency(generator.getInstallmentFrequency().getValue());
                    unit.setMomoAccount(generator.getMobileMoneyAccount());
                    unit.setOutstandingBalance(String.valueOf(generator.getOutstandingBalance().getAmount()));
                    unit.setMacAddress(generator.getMacAddress());
                    //unit.setUserAccount(generator.get);
                    ActivationCodes paymentIdObj = GeneralUtils.convertFromJson(generator.getActivationCodes(), ActivationCodes.class);
                    unit.setActivationCodes(paymentIdObj.getValidActivationCodes());

                    units.add(unit);

                }
            }

        } catch (Exception ex) {

            logger.error("An error occurred while trying to setup an account: " + ex.getMessage());

            status = Status.NOT_LOGGED.getValue();
            statusDescription = "Errors occurred while trying to setup an account: " + ex.getMessage();
        }

        accountSetupResponse.setTelesolaAccount(telesolaAccount);

        String jsonResponse = GeneralUtils.convertToJson(accountSetupResponse, AccountSetupResponse.class);

        return jsonResponse;
    }

    /**
     *
     * @param reportPaymentsRequest
     * @return
     */
    String reportPayments(String reportPaymentsRequest) {

        ReportPaymentsResponse reportPaymentResponse = new ReportPaymentsResponse();
        String jsonResponse;

        try {

            ReportPaymentsRequest reportPaymentRequest = convertFromJson(reportPaymentsRequest, ReportPaymentsRequest.class);

            if (reportPaymentRequest == null) {

                logger.error("unitRegistration is null, failed to un-marshal JSON");

            } else {

                String generatorId = reportPaymentRequest.getParams().getGeneratorId();
                String telesolaAccount = reportPaymentRequest.getParams().getTelesolaAccount();
                String appKey = reportPaymentRequest.getParams().getAppKey();

                int transactionLimit = (reportPaymentRequest.getParams().getTransactionLimit() == null) ? NamedConstants.MAX_NUMBER_PAYMENTS : Integer.parseInt(reportPaymentRequest.getParams().getTransactionLimit());
                OrderFirst orderFirst = OrderFirst.convertToEnum(reportPaymentRequest.getParams().getOrderFirst());
                String status = reportPaymentRequest.getParams().getStatus();
                Status paymentStatus;

                logger.debug("transactionLimit: " + transactionLimit + ", orderFirst: " + orderFirst + ", status: " + status);

                if (status == null || status.trim().isEmpty()) {
                    paymentStatus = Status.ALL;
                } else {
                    paymentStatus = Status.convertToEnum(status);
                }

                List<ReportPaymentsResponse.Data> dataList = new ArrayList<>();

                reportPaymentResponse.setData(dataList);

                Set<MoMoPayment> payments = DBManager.fetchPayments(MoMoPayment.class, transactionLimit, "id", orderFirst, paymentStatus, "generatorId", generatorId);
                GeneratorUnit generatorUnit = DBManager.fetchSingleRecord(GeneratorUnit.class, "generatorId", generatorId);
                Client client = DBManager.fetchSingleRecord(Client.class, "telesolaAccount", telesolaAccount);

                if (payments != null) {

                    logger.debug("And now again, here, payments size: " + payments.size());

                    Iterator<MoMoPayment> paymentsIter = payments.iterator();

                    while (paymentsIter.hasNext()) {

                        MoMoPayment payment = paymentsIter.next();
                        ReportPaymentsResponse.Data data = reportPaymentResponse.new Data();

                        int cummulativeAmountPaid = calculateCummulativeAmountPaid(generatorUnit.getContractPrice(), generatorUnit.getOutstandingBalance());

                        String aggregatorId = payment.getAggregatorTransID();

                        data.setId((int) payment.getId());
                        data.setTelesolaAccount(telesolaAccount);
                        data.setFirstName(client.getName().getFirstname() + " " + client.getName().getSecondname());
                        data.setGeneratorId(payment.getGeneratorId());
                        data.setActivationCode(payment.getActivationCode());
                        data.setMomoAccount(payment.getDebitAccount());
                        data.setAmount(payment.getAmount().getAmount().toString());
                        data.setOutstandingBalance(String.valueOf((int) Math.ceil(generatorUnit.getOutstandingBalance().getAmount().doubleValue())));
                        data.setCummulativeAmountPaid(String.valueOf(cummulativeAmountPaid));
                        data.setInstallmentsPaid(String.valueOf(generatorUnit.getTotalNumberOfInstallmentsToBePaid()));
                        data.setRemaining_installments(String.valueOf(generatorUnit.getTotalNumberOfInstallmentsToBePaid()));
                        data.setAcknowledgeDate("");
                        if (payment.getApprovalDate() != null) {
                            data.setAcknowledgeDate(DateUtils.convertLocalDateTimeToString(payment.getApprovalDate(), NamedConstants.DATE_TIME_DASH_FORMAT));
                        }
                        data.setMomoId(payment.getMomoId());
                        data.setStatus(payment.getStatus().getValue());
                        data.setPaymentDate(DateUtils.convertLocalDateTimeToString(payment.getCreatedOn(), NamedConstants.DATE_TIME_DASH_FORMAT));
                        data.setEnableDuration(String.valueOf(payment.getEnableDuration()));

                        logger.debug("about to add unit to unit list: " + data.getActivationCode() + ", status: " + data.getStatus());

                        dataList.add(data);

                    }
                } else {
                    logger.debug("No Payment transactions found in database");
                }

            }
        } catch (Exception ex) {

            logger.error("An error occurred while trying to fetch payment history : " + ex.getMessage());

        } finally {

            jsonResponse = GeneralUtils.convertToJson(reportPaymentResponse, ReportPaymentsResponse.class);
        }

        return jsonResponse;
    }

    /**
     *
     * @param paymentHistoryPayload
     * @return
     */
    String getPaymentHistory(String paymentHistoryPayload) {

        PaymentHistoryResponse paymentHistResponse = new PaymentHistoryResponse();
        List<PaymentHistoryResponse.Unit> units = new ArrayList<>();
        paymentHistResponse.setUnits(units);

        String jsonResponse;

        try {

            PaymentHistoryRequest paymentHistRequest = convertFromJson(paymentHistoryPayload, PaymentHistoryRequest.class);

            if (paymentHistRequest == null) {

                logger.error("unitRegistration is null, failed to un-marshal JSON");

            } else {
                String generatorId = paymentHistRequest.getParams().getGeneratorId();
                String telesolaAccount = paymentHistRequest.getParams().getTelesolaAccount();
                String appKey = paymentHistRequest.getParams().getAppKey();

                paymentHistResponse.setTelesolaAccount(telesolaAccount);

                int transactionLimit = (paymentHistRequest.getParams().getTransactionLimit() == null) ? NamedConstants.MAX_NUMBER_PAYMENTS : Integer.parseInt(paymentHistRequest.getParams().getTransactionLimit());
                OrderFirst orderFirst = OrderFirst.convertToEnum(paymentHistRequest.getParams().getOrderFirst());
                String status = paymentHistRequest.getParams().getStatus();
                Status paymentStatus;

                logger.debug("transactionLimit: " + transactionLimit + ", orderFirst: " + orderFirst + ", status: " + status);

                if (status == null || status.trim().isEmpty()) {
                    paymentStatus = Status.ALL;
                } else {
                    paymentStatus = Status.convertToEnum(status);
                }

                Set<MoMoPayment> payments = DBManager.fetchPayments(MoMoPayment.class, transactionLimit, "id", orderFirst, paymentStatus, "generatorId", generatorId);
                GeneratorUnit generatorUnit = DBManager.fetchSingleRecord(GeneratorUnit.class, "generatorId", generatorId);

                if (payments != null) {

                    logger.debug("And now again, here, payments size: " + payments.size());

                    Iterator<MoMoPayment> paymentsIter = payments.iterator();

                    while (paymentsIter.hasNext()) {

                        MoMoPayment payment = paymentsIter.next();
                        PaymentHistoryResponse.Unit unit = paymentHistResponse.new Unit();

                        int cummulativeAmountPaid = calculateCummulativeAmountPaid(generatorUnit.getContractPrice(), generatorUnit.getOutstandingBalance());

                        String aggregatorId = payment.getAggregatorTransID();

                        unit.setGeneratorId(payment.getGeneratorId());
                        unit.setActivationCode(payment.getActivationCode());
                        unit.setMomoAccount(payment.getDebitAccount());
                        unit.setAmount(payment.getAmount().getAmount().toString());
                        unit.setOutstandingBalance(String.valueOf((int) Math.ceil(generatorUnit.getOutstandingBalance().getAmount().doubleValue())));
                        unit.setCummulativeAmountPaid(String.valueOf(cummulativeAmountPaid));
                        unit.setInstallmentsPaid(String.valueOf(generatorUnit.getTotalNumberOfInstallmentsToBePaid()));
                        unit.setRemaining_installments(String.valueOf(generatorUnit.getTotalNumberOfInstallmentsToBePaid()));
                        unit.setAcknowledgeDate("");
                        if (payment.getApprovalDate() != null) {
                            unit.setAcknowledgeDate(DateUtils.convertLocalDateTimeToString(payment.getApprovalDate(), NamedConstants.DATE_TIME_DASH_FORMAT));
                        }
                        unit.setMomoId(payment.getMomoId());
                        unit.setStatus(payment.getStatus().getValue());
                        unit.setPaymentDate(DateUtils.convertLocalDateTimeToString(payment.getCreatedOn(), NamedConstants.DATE_TIME_DASH_FORMAT));
                        unit.setEnableDuration(String.valueOf(payment.getEnableDuration()));

                        logger.debug("about to add unit to unit list: " + unit.getActivationCode() + ", status: " + unit.getStatus());

                        units.add(unit);

                    }
                } else {
                    logger.debug("No Payment transactions found in database");
                }
            }

        } catch (Exception ex) {

            logger.error("An error occurred while trying to fetch payment history : " + ex.getMessage());
        } finally {

            jsonResponse = GeneralUtils.convertToJson(paymentHistResponse, PaymentHistoryResponse.class);
        }

        return jsonResponse;
    }

    /**
     *
     * @param paymentHistoryPayload
     * @return
     */
    String getPaymentHistoryModified(String paymentHistoryReportPayload) {

        String jsonResponse;

        try {

            PaymentHistoryRequest paymentHistRequest = convertFromJson(paymentHistoryReportPayload, PaymentHistoryRequest.class);

            if (paymentHistRequest == null) {

                logger.error("unitRegistration is null, failed to un-marshal JSON");

            } else {

                String generatorId = paymentHistRequest.getParams().getGeneratorId();
                String telesolaAccount = paymentHistRequest.getParams().getTelesolaAccount();
                String appKey = paymentHistRequest.getParams().getAppKey();

                int transactionLimit = (paymentHistRequest.getParams().getTransactionLimit() == null) ? NamedConstants.MAX_NUMBER_PAYMENTS : Integer.parseInt(paymentHistRequest.getParams().getTransactionLimit());
                OrderFirst orderFirst = OrderFirst.convertToEnum(paymentHistRequest.getParams().getOrderFirst());
                String status = paymentHistRequest.getParams().getStatus();
                Status paymentStatus;

                logger.debug("transactionLimit: " + transactionLimit + ", orderFirst: " + orderFirst + ", status: " + status);

                if (status == null || status.trim().isEmpty()) {
                    paymentStatus = Status.ALL;
                } else {
                    paymentStatus = Status.convertToEnum(status);
                }

                //String cmsReport = paymentHistRequest.getParams().getRequesType();
                String cmsReport = null;

                logger.debug("We are here!!!");

                if (null != cmsReport) {

                    if (cmsReport.equalsIgnoreCase("CMS_REPORT")) { //put this in ENUM

                        ReportPaymentsResponse paymentHistResponse = new ReportPaymentsResponse();
                        List<ReportPaymentsResponse.Data> dataList = new ArrayList<>();

                        paymentHistResponse.setData(dataList);

                        Set<MoMoPayment> payments = DBManager.fetchPayments(MoMoPayment.class, transactionLimit, "id", orderFirst, paymentStatus, "generatorId", generatorId);
                        GeneratorUnit generatorUnit = DBManager.fetchSingleRecord(GeneratorUnit.class, "generatorId", generatorId);
                        Client client = DBManager.fetchSingleRecord(Client.class, "telesolaAccount", telesolaAccount);

                        if (payments != null) {

                            logger.debug("And now again, here, payments size: " + payments.size());

                            Iterator<MoMoPayment> paymentsIter = payments.iterator();

                            while (paymentsIter.hasNext()) {

                                MoMoPayment payment = paymentsIter.next();
                                ReportPaymentsResponse.Data data = paymentHistResponse.new Data();

                                int cummulativeAmountPaid = calculateCummulativeAmountPaid(generatorUnit.getContractPrice(), generatorUnit.getOutstandingBalance());

                                String aggregatorId = payment.getAggregatorTransID();

                                data.setId((int) payment.getId());
                                data.setTelesolaAccount(telesolaAccount);
                                data.setFirstName(client.getName().getFirstname() + " " + client.getName().getSecondname());
                                data.setGeneratorId(payment.getGeneratorId());
                                data.setActivationCode(payment.getActivationCode());
                                data.setMomoAccount(payment.getDebitAccount());
                                data.setAmount(payment.getAmount().getAmount().toString());
                                data.setOutstandingBalance(String.valueOf((int) Math.ceil(generatorUnit.getOutstandingBalance().getAmount().doubleValue())));
                                data.setCummulativeAmountPaid(String.valueOf(cummulativeAmountPaid));
                                data.setInstallmentsPaid(String.valueOf(generatorUnit.getTotalNumberOfInstallmentsToBePaid()));
                                data.setRemaining_installments(String.valueOf(generatorUnit.getTotalNumberOfInstallmentsToBePaid()));
                                data.setAcknowledgeDate("");
                                if (payment.getApprovalDate() != null) {
                                    data.setAcknowledgeDate(DateUtils.convertLocalDateTimeToString(payment.getApprovalDate(), NamedConstants.DATE_TIME_DASH_FORMAT));
                                }
                                //unit.setAcknowledgeDate(String.valueOf(payment.getApprovalDate()));
                                data.setMomoId(payment.getMomoId());
                                data.setStatus(payment.getStatus().getValue());
                                //unit.setDescription(payment.getStatusDescription());
                                data.setPaymentDate(DateUtils.convertLocalDateTimeToString(payment.getCreatedOn(), NamedConstants.DATE_TIME_DASH_FORMAT));
                                data.setPaymentDate(String.valueOf(payment.getCreatedOn()));
                                data.setEnableDuration(String.valueOf(payment.getEnableDuration()));

                                logger.debug("about to add unit to unit list: " + data.getActivationCode() + ", status: " + data.getStatus());

                                dataList.add(data);

                            }
                        } else {
                            logger.debug("No Payment transactions found in database");
                        }

                        jsonResponse = GeneralUtils.convertToJson(paymentHistResponse, ReportPaymentsResponse.class);

                        return jsonResponse;
                    }
                }

                PaymentHistoryResponse paymentHistResponse = new PaymentHistoryResponse();
                List<PaymentHistoryResponse.Unit> units = new ArrayList<>();

                paymentHistResponse.setUnits(units);
                paymentHistResponse.setTelesolaAccount(telesolaAccount);

                Set<MoMoPayment> payments = DBManager.fetchPayments(MoMoPayment.class, transactionLimit, "id", orderFirst, paymentStatus, "generatorId", generatorId);
                GeneratorUnit generatorUnit = DBManager.fetchSingleRecord(GeneratorUnit.class, "generatorId", generatorId);

                if (payments != null) {

                    logger.debug("And now again, here, payments size: " + payments.size());

                    Iterator<MoMoPayment> paymentsIter = payments.iterator();

                    while (paymentsIter.hasNext()) {

                        MoMoPayment payment = paymentsIter.next();
                        PaymentHistoryResponse.Unit unit = paymentHistResponse.new Unit();

                        int cummulativeAmountPaid = calculateCummulativeAmountPaid(generatorUnit.getContractPrice(), generatorUnit.getOutstandingBalance());

                        String aggregatorId = payment.getAggregatorTransID();

                        unit.setGeneratorId(payment.getGeneratorId());
                        unit.setActivationCode(payment.getActivationCode());
                        unit.setMomoAccount(payment.getDebitAccount());
                        unit.setAmount(payment.getAmount().getAmount().toString());
                        unit.setOutstandingBalance(String.valueOf((int) Math.ceil(generatorUnit.getOutstandingBalance().getAmount().doubleValue())));
                        unit.setCummulativeAmountPaid(String.valueOf(cummulativeAmountPaid));
                        unit.setInstallmentsPaid(String.valueOf(generatorUnit.getTotalNumberOfInstallmentsToBePaid()));
                        unit.setRemaining_installments(String.valueOf(generatorUnit.getTotalNumberOfInstallmentsToBePaid()));
                        unit.setAcknowledgeDate("");
                        if (payment.getApprovalDate() != null) {
                            unit.setAcknowledgeDate(DateUtils.convertLocalDateTimeToString(payment.getApprovalDate(), NamedConstants.DATE_TIME_DASH_FORMAT));
                        }
                        //unit.setAcknowledgeDate(String.valueOf(payment.getApprovalDate()));
                        unit.setMomoId(payment.getMomoId());
                        unit.setStatus(payment.getStatus().getValue());
                        //unit.setDescription(payment.getStatusDescription());
                        unit.setPaymentDate(DateUtils.convertLocalDateTimeToString(payment.getCreatedOn(), NamedConstants.DATE_TIME_DASH_FORMAT));
                        unit.setPaymentDate(String.valueOf(payment.getCreatedOn()));
                        unit.setEnableDuration(String.valueOf(payment.getEnableDuration()));

                        logger.debug("about to add unit to unit list: " + unit.getActivationCode() + ", status: " + unit.getStatus());

                        units.add(unit);

                    }
                } else {
                    logger.debug("No Payment transactions found in database");
                }

                jsonResponse = GeneralUtils.convertToJson(paymentHistResponse, PaymentHistoryResponse.class);

                return jsonResponse;
            }

        } catch (Exception ex) {

            logger.error("An error occurred while trying to fetch payment history : " + ex.getMessage());
        }

        return "{}";
    }

    /**
     * A client is anyone who wishes to take possession of a Generator Unit such
     * as an end-user, Distributor, Telesola employee (wanting to test the data)
     * etc
     *
     * @param registerClientPayload
     * @return
     */
    String registerClient(String registerClientPayload) {

        ClientRegistrationResponse clientRegResponse = new ClientRegistrationResponse();
        String response;

        String status = Status.LOGGED.getValue();
        String statusDescription = "New CLient logged successfully";
        String telesolaAccount = "";

        try {

            //To-Do
            //Add logic to debit client account for the initial deposit if deposit is > 0 
            ClientRegistrationRequest clientRegRequest = convertFromJson(registerClientPayload, ClientRegistrationRequest.class);

            if (clientRegRequest == null) {

                logger.error("unitRegistration is null, failed to un-marshal JSON");

            } else {
                String userAccount = clientRegRequest.getCredentials().getUserAccount();
                String userKey = clientRegRequest.getCredentials().getUserKey();
                String appKey = clientRegRequest.getParams().getAppKey();

                //To-Do
                //Check if userAccount & key are allowed to register Clients
                ClientType clientType = ClientType.convertToEnum(clientRegRequest.getParams().getClientType());
                String primaryContact = GeneralUtils.formatMSISDN(clientRegRequest.getParams().getPrimaryPhone());
                //generate a Telesola account # from the user's primary contact number

                telesolaAccount = GeneralUtils.generateTelesolaAccount(primaryContact, clientType);

                String firstName = clientRegRequest.getParams().getFirstName();
                String secondName = clientRegRequest.getParams().getSecondName();
                String otherPhone = clientRegRequest.getParams().getOtherPhone(); //in months
                String emailAddress = clientRegRequest.getParams().getEmailAddress();
                String physicalAddress = clientRegRequest.getParams().getPhysicalAddress();

                Name name = new Name();
                name.setFirstname(firstName);
                name.setSecondname(secondName);

                Client client = new Client();
                client.setClientStatus(ClientStatus.NORMAL);
                client.setClientType(clientType);
                client.setEmail(emailAddress);
                client.setName(name);
                client.setNumberOfRegisteredUnits(0); //registering client for the very first time
                client.setTelesolaAccount(telesolaAccount);
                client.setRegisteredBy(userAccount);
                client.setPhysicalAddress(physicalAddress);
                client.setPrimaryContactPhone(primaryContact);
                client.setOtherContactPhone(otherPhone);

                long id = DBManager.persistDatabaseModel(client);

                //To-Do 
                //add condition to check for duplicate client and return appropriately
                if (id == -1L) {
                    status = Status.NOT_LOGGED.getValue();
                    statusDescription = "Failed to register client";
                } else {

                    //To-Do
                }
            }

        } catch (Exception ex) {

            logger.error("An error occurred while trying to save Generator Unit: " + ex.getMessage());

            status = Status.NOT_LOGGED.getValue();
            statusDescription = "Errors occurred while trying to save the generator unit: " + ex.getMessage();
            
        } finally {
            
            clientRegResponse.setTelesolaAccount(telesolaAccount);
            clientRegResponse.setStatus(status);
            clientRegResponse.setStatusDescription(statusDescription);

            response = GeneralUtils.convertToJson(clientRegResponse, ClientRegistrationResponse.class);

        }
        
        return response;
    }

    String registerGenerator(String unitRegisterPayload
    ) {

        String status = Status.LOGGED.getValue();
        String statusDescription = "New Generator Unit logged successfully";

        String telesolaAccount = "";
        String generatorId = "";

        int calculatedInstallment = 0;
        int calculatedTotalInstallments = 0;
        int calculatedOutstandingBal = 0;
        int cummulativeTotalPaid = 0;

        try {

            //To-Do
            //Add logic to debit client account for the initial deposit if deposit is > 0 
            UnitRegistrationRequest unitRegistration = convertFromJson(unitRegisterPayload, UnitRegistrationRequest.class
            );

            if (unitRegistration == null) {
                logger.debug("unitRegistration is null, failed to un-marshal JSON");
            }
            String userAccount = unitRegistration.getCredentials().getUserAccount();
            String userKey = unitRegistration.getCredentials().getUserKey();
            String appKey = unitRegistration.getParams().getAppKey();

            telesolaAccount = unitRegistration.getParams().getTelesolaAccount();

            //get the most recently registered generator data
            GeneratorUnit recentGenerator = DBManager.getMostRecentRecord(GeneratorUnit.class, "id");

            if (recentGenerator == null) {
                generatorId = NamedConstants.START_ID;
            } else {
                String idToIncrement = recentGenerator.getGeneratorId();
                generatorId = AlphaNumericIDGenerator.generateNextId(idToIncrement);
            }

            /*int counter = 0;
            while (!generatorId.equalsIgnoreCase("ZZZYY")) {

                generatorId = AlphaNumericIDGenerator.generateNextId(generatorId);
                counter++;

                logger.debug("generated: " + generatorId + " - counter: " + counter);
            }*/
            String contractDateString = unitRegistration.getParams().getContractDate();
            String contractPeriod = unitRegistration.getParams().getContractPeriod(); //in months
            String contractPriceString = unitRegistration.getParams().getContractPrice();
            String depositAmountString = unitRegistration.getParams().getDepositAmount();

            InstallmentDay installmentDay = InstallmentDay.convertToEnum(String.valueOf(unitRegistration.getParams().getInstallmentDay()));
            InstallmentFrequency installmentFrequency = InstallmentFrequency.convertToEnum(unitRegistration.getParams().getInstallmentFrequency());

            String macAddress = unitRegistration.getParams().getMacAddress();
            String mobileMonAccount = unitRegistration.getParams().getMomoAccount();

            CommercialStatus commercialStatus = CommercialStatus.convertToEnum(unitRegistration.getParams().getCommercialStatus());

            Amounttype contractPrice = GeneralUtils.getAmountType(contractPriceString);
            Amounttype depositAmount = GeneralUtils.getAmountType(depositAmountString);

            LocalDate contractDate = DateUtils.convertStringToDate(contractDateString, NamedConstants.DATE_DASH_FORMAT);

            Client client = DBManager.fetchSingleRecord(Client.class, "telesolaAccount", telesolaAccount.toUpperCase());

            if (client == null) {

                logger.error("Cannot register generator unit - Telesola account:" + telesolaAccount + " does not exist");
                status = Status.NOT_LOGGED.getValue();
                statusDescription = "Cannot register generator unit - Telesola account: " + telesolaAccount + ", does not exist";

            } else {

                logger.debug("Got cient with details: " + client.getTelesolaAccount() + ", " + client.getEmail());

                GeneratorUnit generatorUnit = new GeneratorUnit();

                generatorUnit.setPaymentProgress(PaymentProgress.COMPLETED);

                if (commercialStatus == CommercialStatus.INSTALLMENT) {

                    calculatedInstallment = calculateInstallmentAmount(installmentFrequency, contractPeriod, contractPriceString, depositAmountString);
                    calculatedTotalInstallments = calculateTotalNumberOfInstallments(installmentFrequency, contractPeriod);
                    calculatedOutstandingBal = calculateOutstandingBalance(contractPriceString, depositAmountString);

                    generatorUnit.setPaymentProgress(PaymentProgress.NORMAL);
                }

                generatorUnit.setTelesolaAccount(telesolaAccount);
                generatorUnit.setInstallmentAmount(GeneralUtils.getAmountType(String.valueOf(calculatedInstallment)));
                generatorUnit.setCommercialStatus(commercialStatus);
                generatorUnit.setContractDate(contractDate);
                generatorUnit.setContractPrice(contractPrice);
                generatorUnit.setDepositAmount(depositAmount);
                generatorUnit.setContractPeriod(contractPeriod);
                generatorUnit.setGeneratorId(generatorId);
                generatorUnit.setRegisteredTo(client.getClientType());
                generatorUnit.setNumberOfInstallmentsPaid(0); //generator is still new
                generatorUnit.setTotalNumberOfInstallmentsToBePaid(calculatedTotalInstallments);
                generatorUnit.setOutstandingBalance(GeneralUtils.getAmountType(String.valueOf(calculatedOutstandingBal)));
                generatorUnit.setInstallmentFrequency(installmentFrequency);
                generatorUnit.setInstallmentDay(installmentDay);
                generatorUnit.setMobileMoneyAccount(mobileMonAccount);
                generatorUnit.setMacAddress(macAddress);
                generatorUnit.setEnableDurationDefault(GeneralUtils.getEnableDuration(commercialStatus, installmentFrequency));

                Set<Integer> activationCodeSet = GeneralUtils.generateActivationCodes(NamedConstants.NUM_OF_ACTIVATION_CODES, telesolaAccount, generatorId);
                List<Integer> validactivationCodes = GeneralUtils.convertSetToList(activationCodeSet);
                List<Integer> usedActivationCodes = new ArrayList<>();

                ActivationCodes paymentIdObj = new ActivationCodes();
                paymentIdObj.setGeneratorId(generatorId);
                paymentIdObj.setValidActivationCodes(validactivationCodes);
                paymentIdObj.setUsedActivationCodes(usedActivationCodes);

                String paymentIdString = GeneralUtils.convertToJson(paymentIdObj, ActivationCodes.class);

                generatorUnit.setActivationCodes(paymentIdString);

                logger.debug("About to save Generator Unit: " + generatorUnit.toString());

                long id = DBManager.persistDatabaseModel(generatorUnit);

                //To-Do 
                //add condition to check for duplicate generator data and return appropriately
                if (id == -1L) {
                    status = Status.NOT_LOGGED.getValue();
                    statusDescription = "Failed, while saving Generator Unit";
                } else {

                    //update client table with an additional generator registered under them
                    int noOfunits = client.getNumberOfRegisteredUnits();
                    client.setNumberOfRegisteredUnits(++noOfunits);
                    DBManager.updateDatabaseModel(client);

                    //To-Do
                    //send OTP to client for activating the app
                    //send activation code in SMS
                    String otp = String.valueOf(GeneralUtils.generatorOTP(telesolaAccount, generatorId));
                    String smsText = GeneralUtils.getOTPMessage(client.getName().getFirstname(), otp, telesolaAccount, generatorId);
                    String recipientPhone = client.getPrimaryContactPhone();

                    Map<String, String> paramPairs = GeneralUtils.prepareTextMsgParams(smsText, recipientPhone);

                    String url = NamedConstants.SMS_API_URL;

                    String response = AppEntry.clientPool.sendRemoteRequest("", url, paramPairs, HTTPMethod.GET);

                    logger.info("Response from SMS web API Server: " + response);

                }
            }

        } catch (Exception ex) {

            logger.error("An error occurred while trying to save Generator Unit: " + ex.getMessage());

            status = Status.NOT_LOGGED.getValue();
            statusDescription = "Errors occurred while trying to save the generator unit: " + ex.getMessage();
        }

        UnitRegistrationResponse unitRegisterResponse = new UnitRegistrationResponse();
        unitRegisterResponse.setGeneratorId(generatorId);
        unitRegisterResponse.setTelesolaAccount(telesolaAccount);
        unitRegisterResponse.setStatus(status);
        unitRegisterResponse.setStatusDescription(statusDescription);
        unitRegisterResponse.setInstallmentAmount(String.valueOf(calculatedInstallment));

        String jsonResponse = GeneralUtils.convertToJson(unitRegisterResponse, UnitRegistrationResponse.class
        );

        return jsonResponse;

    }

    /**
     * 
     * @param paymentPayload
     * @return 
     */
    String makePayment(String paymentPayload) {

        MakePaymentResponse paymentResponse = new MakePaymentResponse();
        
        //steps to implement
        //1. Log payment to DB
        //2. update the GeneratorUnit table
        //2. respond with LOGGED if ok to client otherwise NOT_LOGGED
        //3. Notify fetcher to send to aggregator via a call back i think
        //transaction.setCreationDate(DateUtils.convertStringToDateTime(creationDate, NamedConstants.DATE_TIME_FORMAT));
        MakePaymentRequest paymentRequest = convertFromJson(paymentPayload, MakePaymentRequest.class
        );

        String status = Status.LOGGED.getValue();
        String statusDescription = "Payment Logged for Processing";

        String telesolaAccount = paymentRequest.getParams().getTelesolaAccount();
        String generatorId = paymentRequest.getParams().getGeneratorId();
        String appKey = paymentRequest.getParams().getAppKey();
        String debitAccount = paymentRequest.getParams().getMomoAccount();

        //get one of the pre-configured and stored valid/unused activation codes
        String activationCode = "";

        Map<String, Object> restrictions = new HashMap<>();
        restrictions.put("generatorId", generatorId);
        restrictions.put("telesolaAccount", telesolaAccount);

        GeneratorUnit generatorUnit = DBManager.fetchSingleRecord(GeneratorUnit.class, restrictions);

        //check generator being paid for, if it exists
        if (generatorUnit == null) {

            status = Status.NOT_LOGGED.getValue();
            statusDescription = "Failed to log payment: Failed to match Unit ID " + generatorId + " and Telesola: " + telesolaAccount;

        } else {

            Amounttype amountToPay = generatorUnit.getInstallmentAmount();

            ActivationCodes paymentIdObj = GeneralUtils.convertFromJson(generatorUnit.getActivationCodes(), ActivationCodes.class);

            //get the ID from the validactivationCodes
            int paymentId = paymentIdObj.getValidActivationCodes().get((paymentIdObj.getValidActivationCodes().size()) - 1);

            String paymentIds = GeneralUtils.convertToJson(paymentIdObj, ActivationCodes.class);
            generatorUnit.setActivationCodes(paymentIds);

            activationCode = String.valueOf(paymentId);

            MoMoPayment payment = new MoMoPayment();

            payment.setGeneratorId(generatorId);
            payment.setTelesolaAccount(telesolaAccount);
            payment.setActivationCode(activationCode);
            payment.setPaymentId(GeneralUtils.generateFullRandomID());
            payment.setAmount(amountToPay);
            payment.setDebitAccount(debitAccount);
            payment.setEnableDuration(0);
            payment.setStatus(Status.LOGGED);
            payment.setDescription("Payment logged successfuly");

            long id = DBManager.persistDatabaseModel(payment);

            if (id == -1L) {
                status = Status.NOT_LOGGED.getValue();
                statusDescription = "Failed to log payment in the database";
            } else {
                //update the GeneratorUnit because of the changes we made above
                DBManager.updateDatabaseModel(generatorUnit);
            }
        }

        //send payment to handler service for processing with aggregator
        //update the GeneratorUnit table
        //response
       
        paymentResponse.setTelesolaAccount(telesolaAccount);
        paymentResponse.setGeneratorId(generatorId);
        paymentResponse.setActivationCode(activationCode);
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
        String paymentId = statusCallbackRequest.getParams().getTransactionId();
        String momoId = statusCallbackRequest.getParams().getMomoId();
        String subscriptionId = statusCallbackRequest.getParams().getSubscriptionId();
        String approvalDate = statusCallbackRequest.getParams().getApprovalDate();
        String statusDescription = statusCallbackRequest.getParams().getStatusMessage();
        String finalStatusDesc = "";
        int statusCode = Integer.parseInt(statusCallbackRequest.getParams().getStatusCode());
        Status finalStatus;

        MoMoPayment payment = DBManager.fetchSingleRecord(MoMoPayment.class, "paymentId", paymentId);
        GeneratorUnit generatorUnit = DBManager.fetchSingleRecord(GeneratorUnit.class, "generatorId", payment.getGeneratorId());

        int amountPaid = (int) Math.ceil(payment.getAmount().getAmount().doubleValue());
        int currTotalNoOfInstallmentsPaid = generatorUnit.getNumberOfInstallmentsPaid();
        int currOutstandingBal = (int) Math.ceil(generatorUnit.getOutstandingBalance().getAmount().doubleValue());
        int newOutstandingBal = currOutstandingBal - amountPaid;

        if (statusCode == NamedConstants.MAMBOPAY_DEBIT_SUCCESS) { //successful

            ActivationCodes activationCodeObj = GeneralUtils.convertFromJson(generatorUnit.getActivationCodes(), ActivationCodes.class);
            //remove the ID from the validactivationCodes
            int activationCode = activationCodeObj.getValidActivationCodes().remove((activationCodeObj.getValidActivationCodes().size()) - 1);
            //add the Id to the usedActivationCodes
            activationCodeObj.getUsedActivationCodes().add(activationCode);

            String activationCodes = GeneralUtils.convertToJson(activationCodeObj, ActivationCodes.class);

            generatorUnit.setActivationCodes(activationCodes);
            generatorUnit.setNumberOfInstallmentsPaid(++currTotalNoOfInstallmentsPaid);
            generatorUnit.setOutstandingBalance(GeneralUtils.getAmountType(String.valueOf(newOutstandingBal)));
            generatorUnit.setPaymentProgress(PaymentProgress.NORMAL);

            if (newOutstandingBal <= 0) {
                generatorUnit.setPaymentProgress(PaymentProgress.COMPLETED);
            }

            finalStatus = Status.SUCCESSFUL;
            finalStatusDesc = "Payment processed successfuly";
            payment.setEnableDuration(generatorUnit.getEnableDurationDefault());

            //update generator table
            DBManager.updateDatabaseModel(generatorUnit);

        } else {

            finalStatus = Status.FAILED;
            finalStatusDesc = "Payment Failed: " + statusDescription;
        }

        //update payments table
        payment.setStatus(finalStatus);
        payment.setStatusDescription(finalStatusDesc);
        payment.setApprovalDate(DateUtils.getDateTimeNow(NamedConstants.KAMPALA_TIME_ZONE));
        payment.setMomoId(momoId);
        payment.setAggregatorTransID(reference);

        DBManager.updateDatabaseModel(payment);

        //send activation code in SMS
        Client client = DBManager.fetchSingleRecord(Client.class, "telesolaAccount", generatorUnit.getTelesolaAccount().toUpperCase());

        int numberOfActiveDays = calculateNumberOfActiveDays(generatorUnit.getInstallmentFrequency());
        String amount = String.valueOf((int) Math.ceil((payment.getAmount().getAmount()).doubleValue()));
        String smsText = GeneralUtils.getActivationCodeMessage(client.getName().getFirstname(), amount, newOutstandingBal, payment.getActivationCode(), numberOfActiveDays);
        String recipientPhone = client.getPrimaryContactPhone();

        Map<String, String> paramPairs = GeneralUtils.prepareTextMsgParams(smsText, recipientPhone);

        String url = NamedConstants.SMS_API_URL;

        String response = AppEntry.clientPool.sendRemoteRequest("", url, paramPairs, HTTPMethod.GET);

        logger.info("Response from SMS web API Server: " + response);

        //response
        statusCallbackResponse.setReference(reference);
        statusCallbackResponse.setTransactionId(paymentId);
        statusCallbackResponse.setAcknowledgeStatus(Status.SUCCESSFUL.getValue());
        statusCallbackResponse.setAcknowledgeDescription("Payment final status acknowledged successfuly");

        String resp = GeneralUtils.convertToJson(statusCallbackResponse, PaymentStatusCallBackResponse.class
        );

        return resp;

    }

    int calculateNumberOfActiveDays(InstallmentFrequency installmentFrequency
    ) {

        int numberOfActiveDays = 0;

        switch (installmentFrequency) {

            case BIWEEKLY:
                numberOfActiveDays = 14;
                break;

            case MONTHLY:
                numberOfActiveDays = 30;
                break;

            case WEEKLY:
                numberOfActiveDays = 7;
                break;

            default:
                numberOfActiveDays = 0;

        }

        return numberOfActiveDays;

    }

    int calculateTotalNumberOfInstallments(InstallmentFrequency installmentFrequency, String contractPeriod
    ) {

        int numberOfInstallments = 0;
        int numberOfWeeksInMonth = 4;
        int numberOfWeeksInHalfMonth = 2;

        if (null != installmentFrequency) {

            switch (installmentFrequency) {

                case MONTHLY:
                    numberOfInstallments = Integer.parseInt(contractPeriod);
                    break;
                case WEEKLY:
                    numberOfInstallments = (int) Math.ceil(Double.parseDouble(contractPeriod) * numberOfWeeksInMonth);
                    break;
                case BIWEEKLY:
                    numberOfInstallments = (int) Math.ceil(Double.parseDouble(contractPeriod) * numberOfWeeksInHalfMonth);
                    break;
                default:
                    break;
            }

        }

        return numberOfInstallments;
    }

    int calculateInstallmentAmount(InstallmentFrequency installmentFrequency, String contractPeriod, String contractPriceString, String depositAmountString
    ) {

        int calculatedInstallment = 0;

        int numberOfWeeksInMonth = 4;
        int numberOfWeeksInHalfMonth = 2;

        double contractPriceAmount = 0f;
        double depositAmount = 0f;

        try {
            contractPriceAmount = GeneralUtils.convertStringToBigDecimal(contractPriceString).doubleValue();
            depositAmount = GeneralUtils.convertStringToBigDecimal(depositAmountString).doubleValue();
        } catch (ParseException ex) {
            logger.error("Failed to get the installment amount: " + ex.getMessage());
        }

        if (null != installmentFrequency) {

            switch (installmentFrequency) {

                case MONTHLY:
                    calculatedInstallment = (int) Math.ceil((contractPriceAmount - depositAmount) / Double.parseDouble(contractPeriod));
                    break;
                case WEEKLY:
                    calculatedInstallment = (int) Math.ceil(((contractPriceAmount - depositAmount) / Double.parseDouble(contractPeriod)) / numberOfWeeksInMonth);
                    break;
                case BIWEEKLY:
                    calculatedInstallment = (int) Math.ceil(((contractPriceAmount - depositAmount) / Double.parseDouble(contractPeriod)) / numberOfWeeksInHalfMonth);
                    break;
                default:
                    break;
            }

        }

        return calculatedInstallment;
    }

    int calculateOutstandingBalance(String contractPriceString, String depositAmountString
    ) {

        return (Integer.parseInt(contractPriceString) - Integer.parseInt(depositAmountString));
    }

    int calculateCummulativeAmountPaid(Amounttype contractAmount, Amounttype outstandingAmount
    ) {

        //return (Integer.parseInt(depositAmountString) + (Integer.parseInt(installmentAmount) * numberOfInstallmentsPaid));
        int contractPrice = (int) Math.ceil(contractAmount.getAmount().doubleValue());
        int outstandingBalance = (int) Math.ceil(outstandingAmount.getAmount().doubleValue());

        return (contractPrice - outstandingBalance);

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
