/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.scheduler;

import com.namaraka.ggserver.constant.HTTPMethod;
import com.namaraka.ggserver.constant.NamedConstants;
import com.namaraka.ggserver.constant.Status;
import com.namaraka.ggserver.dbaccess.DBManager;
import com.namaraka.ggserver.httpmanager.HttpClientPool;
import com.namaraka.ggserver.interfaces.ExecutableJob;
import com.namaraka.ggserver.jsondata.DebitCustomerRequest;
import com.namaraka.ggserver.jsondata.DebitCustomerResponse;
import com.namaraka.ggserver.jsondata.DebitCustomerResponseFail;
import com.namaraka.ggserver.model.v1_0.MoMoPayment;
import com.namaraka.ggserver.utils.DateUtils;
import com.namaraka.ggserver.utils.GeneralUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class doing the work
 *
 * @author smallgod
 */
public class RemainingDaysProcessorJob implements Job, InterruptableJob, ExecutableJob {

    private static final Logger logger = LoggerFactory.getLogger(RemainingDaysProcessorJob.class);

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {

        logger.debug("RemainingDaysProcessorJob running at...: " + DateUtils.getDateTimeNow());

        JobDetail jobDetail = jec.getJobDetail();
        String jobName = jobDetail.getKey().getName();

        JobDataMap jobsDataMap = jec.getMergedJobDataMap();

        JobsData jobsData = (JobsData) jobsDataMap.get(jobName);

        //logger.debug("size of jobMap: " + jobMap.size());
        /*logger.debug("sleeping for 30s at: " + new DateTime().getSecondOfDay());
         try {
         logger.debug("mimick job execution.....");
         Thread.sleep(30000L);
         } catch (InterruptedException ex) {logger.debug("error trying to sleep: " + ex.getMessage());
         }
         logger.debug("done sleeping for 30s at: " + new DateTime().getSecondOfDay());
         */
        Set<MoMoPayment> payments = DBManager.bulkFetchByPropertyName(MoMoPayment.class, "status", Status.LOGGED);

        if (payments != null) {

            logger.info(">>>> NEW, " + payments.size() + " Pending Payments Found!!! <<<<<");

            Iterator<MoMoPayment> iterator = payments.iterator();

            while (iterator.hasNext()) {

                MoMoPayment payment = iterator.next();

                String momoAccount = payment.getDebitAccount();
                String paymentId = String.valueOf(payment.getPaymentId());
                String amount = String.valueOf((int) Math.ceil((payment.getAmount().getAmount()).doubleValue()));

                /*DebitCustomerRequest request = new DebitCustomerRequest();

                request.setAmount(amount);
                request.setCallBackUrl(NamedConstants.GGSERVER_CALLBACK_URL);
                request.setMsisdn(momoAccount);
                request.setTransactionId(paymentId);

                String jsonRequest = GeneralUtils.convertToJson(request, DebitCustomerRequest.class);

                logger.debug("New Request to MamboPay server: " + jsonRequest);*/
                HttpClientPool clientPool = jobsData.getHttpClientPool();

                Map<String, Object> paramPairs = new HashMap<>();

                paramPairs.put(NamedConstants.MAMBOPAY_PARAM_MSISDN, momoAccount);
                paramPairs.put(NamedConstants.MAMBOPAY_PARAM_AMOUNT, amount);
                paramPairs.put(NamedConstants.MAMBOPAY_PARAM_TRANSID, paymentId);
                paramPairs.put(NamedConstants.MAMBOPAY_PARAM_CALLBACKURL, NamedConstants.GGSERVER_CALLBACK_URL);

                String url = NamedConstants.MAMBOPAY_DEBIT_URL;

                String response = clientPool.sendRemoteRequest("", url, paramPairs, HTTPMethod.POST);

                logger.info("Response from MamboPay Server: " + response);

                Status status;
                String message;
                String reference = "";
                //String transactionId;

                DebitCustomerResponse debitResponse;
                DebitCustomerResponseFail debitResponseFail;

                if (!(response == null || response.isEmpty())) {

                    debitResponse = GeneralUtils.convertFromJson(response, DebitCustomerResponse.class);

                    if (debitResponse != null) {

                        String statusCode = debitResponse.getStatusCode();
                        logger.debug("statusCode here: " + statusCode);

                        if (!(statusCode == null || statusCode.isEmpty())) {

                            switch (statusCode) {

                                case NamedConstants.MAMBOPAY_DEBIT_PROCESSING:

                                    reference = debitResponse.getReference();
                                    message = "StatusCode: " + statusCode + ", message: " + debitResponse.getStatusMessage();
                                    debitResponse.getTransactionId();
                                    status = Status.PROCESSING;

                                    break;
                                //To-Do -- Check if status is for duplicate, successfully logged for processing etc and deal accordingly

                                default:
                                    status = Status.FAILED;
                                    message = "Transaction failed, after sending to aggregator: " + debitResponse.getStatusMessage();
                                    break;

                            }

                        } else {

                            debitResponseFail = GeneralUtils.convertFromJson(response, DebitCustomerResponseFail.class);

                            statusCode = debitResponseFail.getStatusCode() != null ? debitResponseFail.getStatusCode() : "";

                            status = Status.FAILED;
                            message = "Failed with status code: " + statusCode + ", message: " + debitResponseFail.getStatusMessage();

                        }

                    } else {

                        debitResponseFail = GeneralUtils.convertFromJson(response, DebitCustomerResponseFail.class);

                        String statusCode = debitResponseFail.getStatusCode() != null ? debitResponseFail.getStatusCode() : "";

                        status = Status.FAILED;
                        message = "Failed with status code: " + statusCode + ", message: " + debitResponseFail.getStatusMessage();

                    }

                } else {
                    status = Status.UNKNOWN;
                    message = "Status is unkown, decide if to re-process or not";
                }

                payment.setAggregatorTransID(reference);
                payment.setStatus(status);
                payment.setStatusDescription(message);

                boolean updated = DBManager.updateDatabaseModel(payment);

                if (!updated) {
                    logger.error("Application Failed to update Payment status, to avoid sending to many requests to Aggregator or MoMo servers, let's terminate the program!!");
                    System.exit(1);

                }
            }

        } else {
            logger.info(">>>> NO Pending Payments Found!!! <<<<<");
        }

    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.warn("Failed to interrupt a Job before deleting it..");
    }

}
