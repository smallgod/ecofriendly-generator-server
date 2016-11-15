package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryResponse {

    /*
    JSON Response sample:
    
   {
        "telesola_account": "786577309",
        "units": [
            {
                "generator_id": "A001",
                "cms_payment_id": "3509866",
                "enable_duration":"7",
                "momo_id": "893783739",
                "momo_account": "256783937043",
                "amount": "59000",
                "payment_date": "2016-09-28 08:55:09",
                "acknowledge_date": "2016-09-28 08:55:09",
                "status": "SUCCESSFUL"
            },
    
            {
                "generator_id": "A002",
                "cms_payment_id": "61866",
                "enable_duration":"30",
                "momo_id": "893783669",
                "momo_account": "256783937043",
                "amount": "58000",
                "payment_date": "2016-07-28 08:55:09",
                "acknowledge_date": "2016-09-28 08:55:09",
                "status": "SUCCESSFUL"
            }
        ]
    }
    
     */
    @SerializedName(value = "telesola_account")
    private String telesolaAccount;

    @SerializedName(value = "units")
    private List<Unit> units;

    public String getTelesolaAccount() {
        return telesolaAccount;
    }

    public void setTelesolaAccount(String telesolaAccount) {
        this.telesolaAccount = telesolaAccount;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    /**
     * Generator Unit representation
     */
    public class Unit {

        @SerializedName(value = "generator_id")
        private String generatorId;

        @SerializedName(value = "cms_payment_id")
        private String cmsPaymentId;

        @SerializedName(value = "momo_id")
        private String momoId;

        @SerializedName(value = "momo_account")
        private String momoAccount;

        @SerializedName(value = "amount")
        private String amount;

        @SerializedName(value = "payment_date")
        private String paymentDate;

        @SerializedName(value = "acknowledge_date")
        private String acknowledgeDate;
        
        @SerializedName(value = "enable_duration")
        private String enableDuration;

        @SerializedName(value = "status")
        private String status;
        
        @SerializedName(value = "description")
        private String description;

        @SerializedName(value = "app_secretkey")
        private String appKey;

        public String getCmsPaymentId() {
            return cmsPaymentId;
        }

        public void setCmsPaymentId(String cmsPaymentId) {
            this.cmsPaymentId = cmsPaymentId;
        }

        public String getGeneratorId() {
            return generatorId;
        }

        public void setGeneratorId(String generatorId) {
            this.generatorId = generatorId;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getMomoId() {
            return momoId;
        }

        public void setMomoId(String momoId) {
            this.momoId = momoId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(String paymentDate) {
            this.paymentDate = paymentDate;
        }

        public String getAcknowledgeDate() {
            return acknowledgeDate;
        }

        public void setAcknowledgeDate(String acknowledgeDate) {
            this.acknowledgeDate = acknowledgeDate;
        }

        public String getMomoAccount() {
            return momoAccount;
        }

        public void setMomoAccount(String momoAccount) {
            this.momoAccount = momoAccount;
        }

        public String getEnableDuration() {
            return enableDuration;
        }

        public void setEnableDuration(String enableDuration) {
            this.enableDuration = enableDuration;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
