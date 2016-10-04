package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class PaymentStatusCallBackResponse {

    /*
    
    //Status callback Response from Seyeya to MamboPay

{
	"reference": "36828394", 
	"transaction_id": "98666882", 
	"acknowledge_status": "SUCCESS",
	"acknowledge_description": "Status acknowledged"
}
    
     */
    @SerializedName(value = "reference")
    private String reference;

    @SerializedName(value = "transaction_id")
    private String transactionId;

    @SerializedName(value = "acknowledge_status")
    private String acknowledgeStatus;

    @SerializedName(value = "acknowledge_description")
    private String acknowledgeDescription;


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAcknowledgeDescription() {
        return acknowledgeDescription;
    }

    public void setAcknowledgeDescription(String acknowledgeDescription) {
        this.acknowledgeDescription = acknowledgeDescription;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAcknowledgeStatus() {
        return acknowledgeStatus;
    }

    public void setAcknowledgeStatus(String acknowledgeStatus) {
        this.acknowledgeStatus = acknowledgeStatus;
    }

}
