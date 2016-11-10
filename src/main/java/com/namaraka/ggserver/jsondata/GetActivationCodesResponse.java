package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GetActivationCodesResponse {

    /*
     JSON Response sample:
    
        {
            "telesola_account": "7749",
            "generator_id": "63566",
            "activation_codes": ['757858', '694844', '84647', '68484']
        }
    
     */
    @SerializedName(value = "telesola_account")
    private String telesolaAccount;

    @SerializedName(value = "generator_id")
    private String generatorId;

    @SerializedName(value = "activation_codes")
    private List<String> activationCodes;

    public String getTelesolaAccount() {
        return telesolaAccount;
    }

    public void setTelesolaAccount(String telesolaAccount) {
        this.telesolaAccount = telesolaAccount;
    }

    public String getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(String generatorId) {
        this.generatorId = generatorId;
    }

    public List<String> getActivationCodes() {
        return activationCodes;
    }

    public void setActivationCodes(List<String> activationCodes) {
        this.activationCodes = activationCodes;
    }
}
