package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountSetupResponse {

    /*
    JSON Response sample:
    
    {
        "telesola_account": "786577309",
        "units": [
            {
                "generator_id": "A001",
                "mac_address": "345S35WET55YH",
                "commercial_status": "INSTALLMENT",
                "contract_date": "2016-09-28 08:55:09",
                "user_account": "DKLA44009",
                "contracted_price": "5450000",
                "installment_frequency": "WEEKLY",
                "enable_duration": "7",
                "installment_day": "1",
                "momo_account": "256774985275",
                "outstanding_balance": "550900",
                "activation_codes": [
                    757853,
                    69434
                ]
            },
            {
                "generator_id": "A002",
                "mac_address": "345S35T2WSH",
                "commercial_status": "INSTALLMENT",
                "contract_date": "2016-04-25 08:55:09",
                "user_account": "DKLA44009",
                "contracted_price": "545000",
                "installment_frequency": "MONTHLY",
                "enable_duration": "30",
                "installment_day": "4",
                "momo_account": "256774985275",
                "outstanding_balance": "30900",
                "activation_codes": [
                    757258,
                    343222,
                    68484
                ]
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

    public class Unit {

        @SerializedName(value = "generator_id")
        private String generatorId;

        @SerializedName(value = "mac_address")
        private String macAddress;
        @SerializedName(value = "commercial_status")
        private String commercialStatus;

        @SerializedName(value = "contract_date")
        private String contractDate;

        @SerializedName(value = "contract_price")
        private String contractPrice;
        
         @SerializedName(value = "user_account") //Telesola # of user who registered this unit
        private String userAccount;

        @SerializedName(value = "installment_frequency")
        private String installmentFrequency;

        @SerializedName(value = "enable_duration")
        private String enableDuration;

        @SerializedName(value = "installment_day")
        private String installmentDay;

        @SerializedName(value = "momo_account")
        private String momoAccount;
        @SerializedName(value = "outstanding_balance")
        private String outstandingBalance;

        @SerializedName(value = "installment_amount")
        private String installmentAmount;

        @SerializedName(value = "activation_codes")
        private List<Integer> activationCodes;

        public String getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        public String getGeneratorId() {
            return generatorId;
        }

        public void setGeneratorId(String generatorId) {
            this.generatorId = generatorId;
        }

        public String getCommercialStatus() {
            return commercialStatus;
        }

        public void setCommercialStatus(String commercialStatus) {
            this.commercialStatus = commercialStatus;
        }

        public String getContractDate() {
            return contractDate;
        }

        public void setContractDate(String contractDate) {
            this.contractDate = contractDate;
        }

        public String getContractPrice() {
            return contractPrice;
        }

        public void setContractPrice(String contractPrice) {
            this.contractPrice = contractPrice;
        }

        public String getInstallmentFrequency() {
            return installmentFrequency;
        }

        public void setInstallmentFrequency(String installmentFrequency) {
            this.installmentFrequency = installmentFrequency;
        }

        public String getEnableDuration() {
            return enableDuration;
        }

        public void setEnableDuration(String enableDuration) {
            this.enableDuration = enableDuration;
        }

        public String getInstallmentDay() {
            return installmentDay;
        }

        public void setInstallmentDay(String installmentDay) {
            this.installmentDay = installmentDay;
        }

        public String getMomoAccount() {
            return momoAccount;
        }

        public void setMomoAccount(String momoAccount) {
            this.momoAccount = momoAccount;
        }

        public String getOutstandingBalance() {
            return outstandingBalance;
        }

        public void setOutstandingBalance(String outstandingBalance) {
            this.outstandingBalance = outstandingBalance;
        }

        public String getInstallmentAmount() {
            return installmentAmount;
        }

        public void setInstallmentAmount(String installmentAmount) {
            this.installmentAmount = installmentAmount;
        }

        public List<Integer> getActivationCodes() {
            return Collections.unmodifiableList(activationCodes);
        }

        public void setActivationCodes(List<Integer> activationCodes) {
            this.activationCodes = activationCodes;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

    }
}
