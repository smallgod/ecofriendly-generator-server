package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class RequestForOTPResponse {

    /*
          {
            "telesola_account": "774987643",
            "primary_phone": "256774986754",
            "status": "SUCCESSFUL",
            "description": "Unit registered successfuly"
        }
     */
    @SerializedName(value = "telesola_account")
    private String telesolaAccount;

    @SerializedName(value = "primary_phone")
    private String primaryPhone;
    
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

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }
    
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

}
