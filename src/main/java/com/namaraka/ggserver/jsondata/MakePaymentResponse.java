package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class MakePaymentResponse {

    /*
    
    {
        ​"telesola_account": "C774983602",
        ​"generator_id"  : "40003",
        ​"status"        : "PENDING",
        ​"activation_code": "79021401"
        ​"description"   : "under processing"

    }
    
     */
    @SerializedName(value = "telesola_account")
    private String telesolaAccount;

    @SerializedName(value = "generator_id")
    private String generatorId;

    @SerializedName(value = "activation_code")
    private String activationCode;

    @SerializedName(value = "status")
    private String status;

    @SerializedName(value = "description")
    private String statusDescription;

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

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

}
