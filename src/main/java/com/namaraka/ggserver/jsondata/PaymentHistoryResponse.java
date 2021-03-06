package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;
import com.namaraka.ggserver.interfaces.CMSReport;
import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryResponse implements CMSReport {

    /*
    JSON Response sample:
    
   {
        "telesola_account": "786577309",
        "status":"SUCCESSFUL",
        "description":"Okay",
        "units": [
            {
                "generator_id": "A001",
                "activation_code": "3509866",
                "enable_duration":"7",
                "remaining_days":"7",
                "momo_id": "893783739",
                "momo_account": "256783937043",
                "amount": "59000",": "550900",
         	"outstanding_balance": "550900",
	        "cumulative_amount_paid": "120000",
	        "number_installments_paid":12
	        "number_installments_remaining":40
                "payment_date": "2016-09-28 08:55:09",
                "acknowledge_date": "2016-09-28 08:55:09",
                "status": "SUCCESSFUL"
            },
    
            {
                "generator_id": "A002",
                "activation_code": "61866",
                "enable_duration":"30",
                "remaining_days":"30",
                "momo_id": "893783669",
                "momo_account": "256783937043",
                "amount": "58000",": "550900",
         	"outstanding_balance": "550900",
	        "cumulative_amount_paid": "120000",
	        "number_installments_paid":12
	        "number_installments_remaining":40
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

        @SerializedName(value = "id")
        private long id;

        @SerializedName(value = "generator_id")
        private String generatorId;

        @SerializedName(value = "activation_code")
        private String activationCode;

        @SerializedName(value = "momo_id")
        private String momoId;

        @SerializedName(value = "momo_account")
        private String momoAccount;

        @SerializedName(value = "amount")
        private String amount;

        @SerializedName(value = "outstanding_balance")
        private String outstandingBalance;

        @SerializedName(value = "cumulative_amount_paid")
        private String cummulativeAmountPaid;

        @SerializedName(value = "number_installments_paid")
        private String installmentsPaid;

        @SerializedName(value = "number_installments_remaining")
        private String remaining_installments;

        @SerializedName(value = "payment_date")
        private String paymentDate;

        @SerializedName(value = "acknowledge_date")
        private String acknowledgeDate;

        @SerializedName(value = "enable_duration")
        private String enableDuration;

        @SerializedName(value = "remaining_days")
        private int remainingDays;

        @SerializedName(value = "next_due_date")
        private String nextDueDate;

        @SerializedName(value = "status")
        private String status;

        @SerializedName(value = "description")
        private String description;

        @SerializedName(value = "app_secretkey")
        private String appKey;

        public String getActivationCode() {
            return activationCode;
        }

        public void setActivationCode(String activationCode) {
            this.activationCode = activationCode;
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

        public String getOutstandingBalance() {
            return outstandingBalance;
        }

        public void setOutstandingBalance(String outstandingBalance) {
            this.outstandingBalance = outstandingBalance;
        }

        public String getCummulativeAmountPaid() {
            return cummulativeAmountPaid;
        }

        public void setCummulativeAmountPaid(String cummulativeAmountPaid) {
            this.cummulativeAmountPaid = cummulativeAmountPaid;
        }

        public String getInstallmentsPaid() {
            return installmentsPaid;
        }

        public void setInstallmentsPaid(String installmentsPaid) {
            this.installmentsPaid = installmentsPaid;
        }

        public String getRemaining_installments() {
            return remaining_installments;
        }

        public void setRemaining_installments(String remaining_installments) {
            this.remaining_installments = remaining_installments;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getRemainingDays() {
            return remainingDays;
        }

        public void setRemainingDays(int remainingDays) {
            this.remainingDays = remainingDays;
        }

        public String getNextDueDate() {
            return nextDueDate;
        }

        public void setNextDueDate(String nextDueDate) {
            this.nextDueDate = nextDueDate;
        }

        @Override
        public String toString() {
            return "Unit{" + "id=" + id + ", generatorId=" + generatorId + ", activationCode=" + activationCode + ", momoId=" + momoId + ", momoAccount=" + momoAccount + ", amount=" + amount + ", outstandingBalance=" + outstandingBalance + ", cummulativeAmountPaid=" + cummulativeAmountPaid + ", installmentsPaid=" + installmentsPaid + ", remaining_installments=" + remaining_installments + ", paymentDate=" + paymentDate + ", acknowledgeDate=" + acknowledgeDate + ", enableDuration=" + enableDuration + ", remainingDays=" + remainingDays + ", nextDueDate=" + nextDueDate + ", status=" + status + ", description=" + description + ", appKey=" + appKey + '}';
        }
        
        
    }
}
