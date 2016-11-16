package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;

public class ActivationCodes {

    /*
     JSON sample:
    
        {
            "generator_id": "63566",
            "valid_activation_codes": [757858, 694844, 84647, 68484],
            "used_activation_codes": [753858, 692844, 24647, 56484]
        }
    
     */
    @SerializedName(value = "generator_id")
    private String generatorId;

    @SerializedName(value = "activation_codes")
    private List<Integer> validActivationCodes;
    
    @SerializedName(value = "used_activation_codes")
    private List<Integer> usedActivationCodes;

    public String getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(String generatorId) {
        this.generatorId = generatorId;
    }

    public List<Integer> getValidActivationCodes() {
        return validActivationCodes;
    }

    public void setValidActivationCodes(List<Integer> ValidPaymentIds) {
        this.validActivationCodes = ValidPaymentIds;
    }

    public List<Integer> getUsedActivationCodes() {
        return usedActivationCodes;
    }

    public void setUsedActivationCodes(List<Integer> usedActivationCodes) {
        this.usedActivationCodes = usedActivationCodes;
    }
}
