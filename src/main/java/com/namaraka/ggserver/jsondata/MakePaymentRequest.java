package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class MakePaymentRequest  {

    /*
    
    {​
        "method":"MAKE_PAYMENT",
    
        "params": {
            "user_id"       : "0774983602",
​​            "app_secretkey" : "32254kUHE39AH3P90EQ",
            "creation_date" : "2016-07-25 08:55:09",
  ​​          "invoice_number": "INV00003",
            "amount"        : "50000",
            "currency"      : "50000", //add this one to API
            "debit_account" : "0774983602", //add this one to API
            "payer_narration" : "3rd installment"
​        },
    
        "extra": {
            ​​"extra1"        :"value1",
            ​​"extra2"        :"value2",
            ​​"extra3"        :"value3"
​        }
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

        @SerializedName(value = "user_id")
        private String userId;

        @SerializedName(value = "app_secretkey")
        private String appKey;
        
        @SerializedName(value = "creation_date")
        private String creationDate;
        
        @SerializedName(value = "invoice_number")
        private String invoiceNumber;
        
        @SerializedName(value = "amount")
        private String amount;
        
        @SerializedName(value = "currency")
        private String currency;
        
        @SerializedName(value = "payer_narration")
        private String payerNarration;
        
        @SerializedName(value = "debit_account")
        private String debitAccount;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getInvoiceNumber() {
            return invoiceNumber;
        }

        public void setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDebitAccount() {
            return debitAccount;
        }

        public void setDebitAccount(String debitAccount) {
            this.debitAccount = debitAccount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPayerNarration() {
            return payerNarration;
        }

        public void setPayerNarration(String payerNarration) {
            this.payerNarration = payerNarration;
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
