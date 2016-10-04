package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class PaymentStatusCallBackRequest  {

    /*
    
//Status callback Request from MamboPay to Seyeya

{

	"method": "PAYMENT_STATUS_CALLBACK", 

	"params": {
		"reference": "36828394",
		"transaction_id": "98666882",
		"momo_id": "456326",     //MNO mobile money ID for our reference
		"subscription_id": "32254kUHE39AH3P90EQ", 
		"approval_date": "2016-07-25 08:55:09",   //full date-time when user approves payment
		"status_code": "200",
		"status_message": "Successfuly processed"
	},

	"extra": {
		"extra1": "value1", 
		"extra2": "", 
		"extra3": ""
	}
}

    
    */
 
    @SerializedName(value = "method")
    private String methodName;

    @SerializedName(value = "params")
    private Params params;
    
    @SerializedName(value = "extra")
    private Extra extra;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public class Params {

        @SerializedName(value = "reference")
        private String reference;

        @SerializedName(value = "transaction_id")
        private String transactionId;
        
        @SerializedName(value = "momo_id")
        private String momoId;
        
        @SerializedName(value = "subscription_id")
        private String subscriptionId;
        
        @SerializedName(value = "approval_date")
        private String approvalDate;
        
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

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getSubscriptionId() {
            return subscriptionId;
        }

        public void setSubscriptionId(String subscriptionId) {
            this.subscriptionId = subscriptionId;
        }

        public String getMomoId() {
            return momoId;
        }

        public void setMomoId(String momoId) {
            this.momoId = momoId;
        }

        public String getApprovalDate() {
            return approvalDate;
        }

        public void setApprovalDate(String approvalDate) {
            this.approvalDate = approvalDate;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getStatusMessage() {
            return statusMessage;
        }

        public void setStatusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
        }
    }
    
    
    public class Extra {
        

        @SerializedName(value = "extra1")
        private String extra1;

        @SerializedName(value = "extra2")
        private String extra2;
        
        @SerializedName(value = "extra3")
        private String extra3;

        public String getExtra1() {
            return extra1;
        }

        public void setExtra1(String extra1) {
            this.extra1 = extra1;
        }

        public String getExtra2() {
            return extra2;
        }

        public void setExtra2(String extra2) {
            this.extra2 = extra2;
        }

        public String getExtra3() {
            return extra3;
        }

        public void setExtra3(String extra3) {
            this.extra3 = extra3;
        }
    }
}
