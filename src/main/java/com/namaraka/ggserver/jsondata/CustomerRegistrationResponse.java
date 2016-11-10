package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class CustomerRegistrationResponse {

    /*
    {
        ​"telesola_account"  : "774983602",
        ​"status"            : "LOGGED",
        ​"description"       : "New customer logged"

    }
     */
    @SerializedName(value = "telesola_account")
    private String telesolaAccount;

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

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

}
