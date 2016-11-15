package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;

public class PaymentIds {

    /*
     JSON sample:
    
        {
            "generator_id": "63566",
            "valid_payment_ids": [757858, 694844, 84647, 68484],
            "used_payment_ids": [753858, 692844, 24647, 56484]
        }
    
     */
    @SerializedName(value = "generator_id")
    private String generatorId;

    @SerializedName(value = "valid_payment_ids")
    private List<Integer> validPaymentIds;
    
    @SerializedName(value = "used_payment_ids")
    private List<Integer> usedPaymentIds;

    public String getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(String generatorId) {
        this.generatorId = generatorId;
    }

    public List<Integer> getValidPaymentIds() {
        return Collections.unmodifiableList(validPaymentIds);
    }

    public void setValidPaymentIds(List<Integer> ValidPaymentIds) {
        this.validPaymentIds = ValidPaymentIds;
    }

    public List<Integer> getUsedPaymentIds() {
        return usedPaymentIds;
    }

    public void setUsedPaymentIds(List<Integer> usedPaymentIds) {
        this.usedPaymentIds = usedPaymentIds;
    }
}
