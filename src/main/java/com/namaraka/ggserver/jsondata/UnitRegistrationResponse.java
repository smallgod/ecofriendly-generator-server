package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class UnitRegistrationResponse {

    /*
          {
            "telesola_account": "774987643",
            "generator_id": "A001",
            "installment_amount": "50000",
            "status": "SUCCESSFUL",
            "description": "Unit registered successfuly"
        }
     */
    @SerializedName(value = "telesola_account")
    private String telesolaAccount;

    @SerializedName(value = "generator_id")
    private String generatorId;

    @SerializedName(value = "installment_amount")
    private String installmentAmount;

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

    public String getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

}
