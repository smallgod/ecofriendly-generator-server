/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.scheduler;

import com.namaraka.ggserver.constant.NamedConstants;
import com.namaraka.ggserver.constant.Status;
import com.namaraka.ggserver.dbaccess.DBManager;
import com.namaraka.ggserver.httpmanager.HttpClientPool;
import com.namaraka.ggserver.interfaces.ExecutableJob;
import com.namaraka.ggserver.jsondata.DebitCustomerRequest;
import com.namaraka.ggserver.jsondata.DebitCustomerResponse;
import com.namaraka.ggserver.model.v1_0.MoMoTransaction;
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
public class PaymentProcessorJob implements Job, InterruptableJob, ExecutableJob {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessorJob.class);

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {

        logger.debug("PaymentProcessorJob running...");

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
        Set<MoMoTransaction> transactions = DBManager.bulkFetchByPropertyName(MoMoTransaction.class, "status", Status.LOGGED);

        if (transactions != null) {

            logger.info(">>>> NEW, " + transactions.size() + " Pending Payments Found!!! <<<<<");

            Iterator<MoMoTransaction> iterator = transactions.iterator();

            while (iterator.hasNext()) {

                MoMoTransaction transaction = iterator.next();

                String momoAccount = transaction.getDebitAccount();
                String transactionId = transaction.getCmsTransactionID();
                String amount = String.valueOf((int) Math.ceil((transaction.getAmount().getAmount()).doubleValue()));

                DebitCustomerRequest request = new DebitCustomerRequest();

                request.setAmount(amount);
                request.setCallBackUrl(NamedConstants.GGSERVER_CALLBACK_URL);
                request.setMsisdn(momoAccount);
                request.setTransactionId(transactionId);

                String jsonRequest = GeneralUtils.convertToJson(request, DebitCustomerRequest.class);

                logger.debug("New Request to MamboPay server: " + jsonRequest);

                HttpClientPool clientPool = jobsData.getHttpClientPool();

                Map<String, String> paramPairs = new HashMap<>();

                paramPairs.put("Ocp-Apim-Subscription-Key", NamedConstants.SUBSCRIPTION_KEY);
                String url = NamedConstants.MAMBOPAY_DEBIT_URL;

                String response = clientPool.sendRemoteRequest(jsonRequest, url, paramPairs);

                logger.info("Response from MamboPay Server: " + response);

                //update transactions table
                DebitCustomerResponse debitResponse = GeneralUtils.convertFromJson(response, DebitCustomerResponse.class);

                Status status;
                String message;

                if (debitResponse != null) {

                    debitResponse.getReference();
                    debitResponse.getStatusCode();
                    debitResponse.getStatusMessage();
                    debitResponse.getTransactionId();

                    /*
                    [09/09/2016, 17:35:51] Charles Muhindo: "101" your account is not registered on MTN Mobile Money
                    [09/09/2016, 17:36:17] Charles Muhindo: "106" you do not have sufficient funds in your mobile money account.
                    [09/09/2016, 17:42:41] Charles Muhindo: '105' "You are below minimum amount threshold e.g below 500/="
                    [09/09/2016, 17:49:42] Charles Muhindo: 103 Customer did not approve transaction
                     */
                    //To-Do
                    //Check if status is for duplicate, successfully logged for processing etc and deal accordingly
                    if (debitResponse.getStatusCode().equalsIgnoreCase(NamedConstants.MAMBOPAY_DEBIT_PROCESSING)) {
                        status = Status.PROCESSING;
                        message = debitResponse.getStatusMessage();
                    } else {
                        status = Status.FAILED;
                        message = "Transaction failed, after sending to aggregator: " + debitResponse.getStatusMessage();
                    }

                } else {
                    status = Status.UNKNOWN;
                    message = "Status is unkown, decide if to re-process or not";
                }

                transaction.setStatus(status);
                transaction.setDescription(message);

                DBManager.updateDatabaseModel(transaction);
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
