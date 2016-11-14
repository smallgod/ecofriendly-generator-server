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
import com.namaraka.ggserver.constant.InstallmentDay;
import com.namaraka.ggserver.constant.InstallmentFrequency;
import com.namaraka.ggserver.constant.NamedConstants;
import com.namaraka.ggserver.constant.PaymentProgress;
import com.namaraka.ggserver.constant.Status;
import com.namaraka.ggserver.dbaccess.DBManager;
import com.namaraka.ggserver.jsondata.ClientRegistrationRequest;
import com.namaraka.ggserver.jsondata.ClientRegistrationResponse;
import com.namaraka.ggserver.jsondata.MakePaymentRequest;
import com.namaraka.ggserver.jsondata.MakePaymentResponse;
import com.namaraka.ggserver.jsondata.PaymentStatusCallBackRequest;
import com.namaraka.ggserver.jsondata.PaymentStatusCallBackResponse;
import com.namaraka.ggserver.jsondata.UnitRegistrationRequest;
import com.namaraka.ggserver.jsondata.UnitRegistrationResponse;
import com.namaraka.ggserver.model.v1_0.Amounttype;
import com.namaraka.ggserver.model.v1_0.Client;
import com.namaraka.ggserver.model.v1_0.GeneratorUnit;
import com.namaraka.ggserver.model.v1_0.MoMoTransaction;
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
import org.joda.time.LocalDateTime;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import static com.namaraka.ggserver.utils.GeneralUtils.convertFromJson;
import com.namaraka.ggserver.utils.IdGenerator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        response.addHeader("Access-Control-Allow-Origin", "*");
        //headers.add("Access-Control-Allow-Origin", "http://podcastpedia.org"); //allows CORS requests only coming from podcastpedia.org		
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
        response.addHeader("Access-Control-Allow-Credentials", "true");

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

            case PAYMENT_STATUS:
                System.out.println("PAYMENT_STATUS called");
                jsonResponse = "{\"telesola_account\":\"0774983602\",\"generator_id\":\"A001\",\"cms_payment_id\":\"794001\",\"momo_id\":\"23577744357\",\"payment_date\":\"2016-07-25 08:55:09\",\"enable_duration\":\"7\",\"activation_code\":\"5784\",\"status\":\"SUCCESSFUL\",\"description\":\"Processing processed successfully\"}";
                break;

            case PAYMENT_HISTORY:
                System.out.println("PAYMENT_HISTORY called");
                jsonResponse = "{\"telesola_account\":\"786577309\",\"units\":[{\"generator_id\":\"A001\",\"cms_payment_id\":\"3509866\",\"enable_duration\":\"7\",\"momo_id\":\"893783739\",\"momo_account\":\"256783937043\",\"amount\":\"59000\",\"payment_date\":\"2016-09-28 08:55:09\",\"acknowledge_date\":\"2016-09-28 08:55:09\"},{\"generator_id\":\"A002\",\"cms_payment_id\":\"61866\",\"momo_id\":\"893783669\",\"momo_account\":\"256783937043\",\"amount\":\"58000\",\"payment_date\":\"2016-07-28 08:55:09\",\"acknowledge_date\":\"2016-09-28 08:55:09\"}]}";
                break;
            case ACCOUNT_SETUP:
                System.out.println("ACCOUNT_SETUP called");
                jsonResponse = "{\"telesola_account\":\"C786577309\",\"units\":[{\"generator_id\":\"A001\",\"mac_address\":\"345S35WET55YH\",\"commercial_status\":\"INSTALLMENT\",\"contract_date\":\"2016-09-28 08:55:09\",\"distributor_id\":\"KLA44009\",\"distributor_key\":\"561277\",\"contracted_price\":\"5450000\",\"installment_frequency\":\"WEEKLY\",\"enable_duration\":\"7\",\"installment_day\":\"1\",\"momo_account\":\"256774985275\",\"outstanding_balance\":\"550900\",\"activation_codes\":[757853,69434]},{\"generator_id\":\"A002\",\"mac_address\":\"345S35T2WSH\",\"commercial_status\":\"INSTALLMENT\",\"contract_date\":\"2016-04-25 08:55:09\",\"distributor_id\":\"KLA84019\",\"distributor_key\":\"5644277\",\"contracted_price\":\"545000\",\"installment_frequency\":\"MONTHLY\",\"enable_duration\":\"30\",\"installment_day\":\"4\",\"momo_account\":\"256774985275\",\"outstanding_balance\":\"30900\",\"activation_codes\":[757258,343222,68484]}]}";
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

    /**
     * A client is anyone who wishes to take possession of a Generator Unit such
     * as an end-user, Distributor, Telesola employee (wanting to test the unit)
     * etc
     *
     * @param registerClientPayload
     * @return
     */
    String registerClient(String registerClientPayload) {

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
                ClientType clientType = ClientType.valueOf(clientRegRequest.getParams().getClientType());
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
        }

        logger.debug("about to convert object to json resopnse");

        ClientRegistrationResponse clientRegResponse = new ClientRegistrationResponse();
        clientRegResponse.setTelesolaAccount(telesolaAccount);
        clientRegResponse.setStatus(status);
        clientRegResponse.setStatusDescription(statusDescription);

        String jsonResponse = GeneralUtils.convertToJson(clientRegResponse, ClientRegistrationResponse.class);

        return jsonResponse;
    }

    public String generateUnitId1(String number) {
        Pattern compile = Pattern.compile("^(.*?)([0-9]*|[A-Z]*)$");
        Matcher matcher = compile.matcher(number);
        String remaining = number;
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

    String generateUnitId2(String number) {

        Pattern compile = Pattern.compile("^(.*?)([9Z]*)$");
        Matcher matcher = compile.matcher(number);
        String left = "";
        String right = "";
        if (matcher.matches()) {
            left = matcher.group(1);
            right = matcher.group(2);
        }
        number = !left.isEmpty() ? Long.toString(Long.parseLong(left, 36) + 1, 36) : "";
        number += right.replace("Z", "A").replace("9", "0");
        return number.toUpperCase();

    }

    public String generateUnitId(String number) {
        
        char[] cars = number.toUpperCase().toCharArray();
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

    String registerGenerator(String unitRegisterPayload) {

        logger.debug("about to Register unit!!");

        String status = Status.LOGGED.getValue();
        String statusDescription = "New Generator Unit logged successfully";

        String telesolaAccount = "";
        String generatorId = "";

        int calculatedInstallment = 0;
        int calculatedTotalInstallments = 0;
        int calculatedOutstandingBal = 0;

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

            String number = "001";
            long counter = 0L;
            /*while (!number.equalsIgnoreCase("ZZZZZZ")) {

                number = generateUnitId(number);
                logger.debug("generated: " + number);
                counter++;
            }*/
            
            IdGenerator.generateId(number);

            //logger.debug("Going out of the loop after generating: [" + counter + "] numbers");

            //To-DO generate a generator ID from the unit's mac address
            generatorId = generateUnitId("ABAA0000");

            String contractDateString = unitRegistration.getParams().getContractDate();
            String contractPeriod = unitRegistration.getParams().getContractPeriod(); //in months
            String contractPriceString = unitRegistration.getParams().getContractPrice();
            String depositAmountString = unitRegistration.getParams().getDepositAmount();

            InstallmentDay installmentDay = InstallmentDay.convertToEnum(unitRegistration.getParams().getInstallmentDay());
            InstallmentFrequency installmentFrequency = InstallmentFrequency.convertToEnum(unitRegistration.getParams().getInstallmentFrequency());

            String macAddress = unitRegistration.getParams().getMacAddress();
            String mobileMonAccount = unitRegistration.getParams().getMomoAccount();

            CommercialStatus commercialStatus = CommercialStatus.convertToEnum(unitRegistration.getParams().getCommercialStatus());

            Amounttype contractPrice = GeneralUtils.getAmountType(contractPriceString);
            Amounttype depositAmount = GeneralUtils.getAmountType(depositAmountString);

            LocalDateTime contractDate = DateUtils.convertStringToDate(contractDateString, NamedConstants.DATE_TIME_DASH_FORMAT);

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

                logger.debug("About to save Generator Unit: " + generatorUnit.toString());

                long id = DBManager.persistDatabaseModel(generatorUnit);

                //To-Do 
                //add condition to check for duplicate generator unit and return appropriately
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

    //String accountSetup() {}
    //String paymentHistory() {}
    String makePayment(String paymentJsonRequest) {

        //steps to implement
        //1. Log payment to DB
        //2. update the GeneratorUnit table
        //2. respond with LOGGED if ok to client otherwise NOT_LOGGED
        //3. Notify fetcher to send to aggregator via a call back i think
        //transaction.setCreationDate(DateUtils.convertStringToDate(creationDate, NamedConstants.DATE_TIME_FORMAT));
        MakePaymentRequest paymentRequest = convertFromJson(paymentJsonRequest, MakePaymentRequest.class
        );

        String status = Status.LOGGED.getValue();
        String statusDescription = "Payment Logged for Processing";

        String telesolaAccount = paymentRequest.getParams().getTelesolaAccount();
        String generatorId = paymentRequest.getParams().getGeneratorId();
        String appKey = paymentRequest.getParams().getAppKey();
        String cmsTransactionID = GeneralUtils.generateShorterRandomID();
        String debitAccount = paymentRequest.getParams().getMomoAccount();

        GeneratorUnit generatorUnit = DBManager.fetchRecord(GeneratorUnit.class, "generatorId", generatorId);

        //check generator being paid for, if it exists
        if (generatorUnit == null) {

            status = Status.NOT_LOGGED.getValue();
            statusDescription = "Failed to log payment: Generator unit with ID: " + generatorId + ", doesn't exist";

        } else {

            Amounttype amountToPay = generatorUnit.getInstallmentAmount();

            MoMoTransaction transaction = new MoMoTransaction();

            transaction.setGeneratorId(generatorId);
            transaction.setTelesolaAccount(telesolaAccount);
            transaction.setCmsTransactionID(cmsTransactionID);
            transaction.setAmount(amountToPay);
            transaction.setDebitAccount(debitAccount);

            //cumm. total
            //outstanding bal.
            long id = DBManager.persistDatabaseModel(transaction);

            if (id == -1L) {
                status = Status.NOT_LOGGED.getValue();
                statusDescription = "Failed to log payment in the database";
            }
        }

        //send payment to handler service for processing with aggregator
        //update the GeneratorUnit table
        //response
        MakePaymentResponse paymentResponse = new MakePaymentResponse();
        paymentResponse.setTelesolaAccount(telesolaAccount);
        paymentResponse.setGeneratorId(generatorId);
        paymentResponse.setCmsPaymentId(cmsTransactionID);
        paymentResponse.setStatus(status);
        paymentResponse.setStatusDescription(statusDescription);

        String resp = GeneralUtils.convertToJson(paymentResponse, MakePaymentResponse.class
        );

        return resp;

    }

    String paymentStatusCallBackResponse(String paymentStatusJsonRequest) {

        //steps to implement
        //1. Log payment to DB
        //2. respond with LOGGED if ok to client otherwise NOT_LOGGED
        //3. 
        PaymentStatusCallBackRequest statusCallbackRequest = convertFromJson(paymentStatusJsonRequest, PaymentStatusCallBackRequest.class
        );

        PaymentStatusCallBackResponse statusCallbackResponse = new PaymentStatusCallBackResponse();

        String reference = statusCallbackRequest.getParams().getReference();
        String transactionId = statusCallbackRequest.getParams().getTransactionId();
        String momoId = statusCallbackRequest.getParams().getMomoId();
        String subscriptionId = statusCallbackRequest.getParams().getSubscriptionId();
        String approvalDate = statusCallbackRequest.getParams().getApprovalDate();
        String statusMessage = statusCallbackRequest.getParams().getStatusMessage();
        int statusCode = Integer.parseInt(statusCallbackRequest.getParams().getStatusCode());
        String finalStatus;

        MoMoTransaction transaction = DBManager.fetchRecord(MoMoTransaction.class, "cmsTransactionID", transactionId);
        GeneratorUnit generatorUnit = DBManager.fetchRecord(GeneratorUnit.class, "generatorId", transaction.getGeneratorId());

        int amountPaid = (int) Math.ceil(transaction.getAmount().getAmount().doubleValue());
        int currTotalNoOfInstallmentsPaid = generatorUnit.getNumberOfInstallmentsPaid();
        int currOutstandingBal = (int) Math.ceil(generatorUnit.getOutstandingBalance().getAmount().doubleValue());
        int newOutstandingBal = currOutstandingBal - amountPaid;

        if (statusCode == 200) { //successful

            finalStatus = Status.SUCCESSFUL.getValue();
            generatorUnit.setNumberOfInstallmentsPaid(++currTotalNoOfInstallmentsPaid);
            generatorUnit.setOutstandingBalance(GeneralUtils.getAmountType(String.valueOf(newOutstandingBal)));
            generatorUnit.setPaymentProgress(PaymentProgress.NORMAL);

            if (newOutstandingBal <= 0) {
                generatorUnit.setPaymentProgress(PaymentProgress.COMPLETED);
            }

            //update generator table
            DBManager.updateDatabaseModel(generatorUnit);

            transaction.setStatus(Status.SUCCESSFUL);

        } else {

            finalStatus = Status.FAILED.getValue();

            transaction.setStatus(Status.FAILED);
        }

        //update transactions table
        transaction.setApprovalDate(DateUtils.getDateTimeNow(NamedConstants.KAMPALA_TIME_ZONE));
        transaction.setMomoId(momoId);
        transaction.setAggregatorTransID(reference);

        DBManager.updateDatabaseModel(transaction);

        //response
        statusCallbackResponse.setReference(reference);
        statusCallbackResponse.setTransactionId(transactionId);
        statusCallbackResponse.setAcknowledgeStatus(finalStatus);
        statusCallbackResponse.setAcknowledgeDescription(statusMessage);

        String resp = GeneralUtils.convertToJson(statusCallbackResponse, PaymentStatusCallBackResponse.class
        );

        return resp;

    }

    int calculateTotalNumberOfInstallments(InstallmentFrequency installmentFrequency, String contractPeriod) {

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

    int calculateInstallmentAmount(InstallmentFrequency installmentFrequency, String contractPeriod, String contractPriceString, String depositAmountString) {

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

    int calculateOutstandingBalance(String contractPriceString, String depositAmountString) {

        return (Integer.parseInt(contractPriceString) - Integer.parseInt(depositAmountString));
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
