package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class DebitCustomerRequest {

    /*
    
    JSON Request sample:
    
        {
            "msisdn":"256774983602",
            "amount":"500",
            "transaction_id":"556723470",
            "callbackurl":"http://namaraka.com:9099/api/json"
        }
    
     */
    @SerializedName(value = "msisdn")
    private String msisdn;

    @SerializedName(value = "amount")
    private String amount;

    @SerializedName(value = "transaction_id")
    private String transactionId;

    @SerializedName(value = "callbackurl")
    private String callBackUrl;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }
}
