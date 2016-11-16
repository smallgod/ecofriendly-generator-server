package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class PaymentStatusResponse {

    /*
         {
            "telesola_account": "C774983602",
            "generator_id": "A00001",
            "momo_id"       : "452156",
​​            "payment_date" : "2016-07-25 08:55:09",
            "enable_duration" : "7",
            "outstanding_balance" : "70900",
  ​​          "activation_code": "58795942",
            "status": "SUCCESSFUL",
            "description": "Payment Successfully processed"
        }
    
     */
    @SerializedName(value = "telesola_account")
    private String telesolaAccount;

    @SerializedName(value = "generator_id")
    private String generatorId;

    @SerializedName(value = "momo_id")
    private String momoId;

    @SerializedName(value = "payment_date")
    private String paymentDate;

    @SerializedName(value = "enable_duration")
    private String enableDuration;

    @SerializedName(value = "activation_code")
    private String activationCode;

    @SerializedName(value = "status")
    private String status;

    @SerializedName(value = "description")
    private String statusDescription;
    
    @SerializedName(value = "outstanding_balance")
    private String outstandingBalance;

    public String getTelesolaAccount() {
        return telesolaAccount;
    }

    public void setTelesolaAccount(String telesolaAccount) {
        this.telesolaAccount = telesolaAccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(String generatorId) {
        this.generatorId = generatorId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getMomoId() {
        return momoId;
    }

    public void setMomoId(String momoId) {
        this.momoId = momoId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getEnableDuration() {
        return enableDuration;
    }

    public void setEnableDuration(String enableDuration) {
        this.enableDuration = enableDuration;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(String outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

}
