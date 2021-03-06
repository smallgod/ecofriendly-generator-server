package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class DebitCustomerResponse {

    /*
    

    JSON Request sample:

    {
    
        "reference":745115924,
        "transaction_id":"33322345",
        "status_code":"01",
        "status_message":"Transaction Queued for processing"
    }
    
    { 
    "statusCode": 401, 
    "message": "Access denied due to missing subscription key. Make sure to include subscription key when making requests to an API." }
   
     */
    @SerializedName(value = "reference")
    private String reference;

    @SerializedName(value = "transaction_id")
    private String transactionId;

    @SerializedName(value = "status_code")
    private String statusCode;

    @SerializedName(value = "status_message")
    private String statusMessage;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
