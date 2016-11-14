package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class UnitRegistrationRequest {

    /*
    
     JSON Request sample:
    
    //in one line:   {"method":"REGISTER_UNIT","credentials":{"user_account":"DKLA84009","user_key":"5644777"},"params":{"telesola_account":"C786577309","mac_address":"3EDFY7654RTYU9","momo_account":"256774794077","commercial_status":"INSTALLMENT","contract_date":"2016-07-25 08:55:09","contract_period":"24","contract_price":"540000","deposit_amount":"40000","installment_frequency":"WEEKLY","installment_day":"5","app_secretkey":"32254kUHE39AH3P90EQ"},"extra":{"extra1":"value1","extra2":"value2","extra3":"value3"}}    
    {
            "method": "REGISTER_UNIT",
            "credentials": {
                "user_account": "DKLA84009",
                "user_key": "5644777"
            },
            "params": {
                "telesola_account": "C786577309",
                "mac_address": "3EDFY7654RTYU9",
                "momo_account": "256774794077",
                "commercial_status": "INSTALLMENT",
                "contract_date": "2016-07-25 08:55:09",
                "contract_period": "24",
                "contract_price": "540000",
                "deposit_amount": "40000",
                "installment_frequency": "WEEKLY",
                "installment_day": "5",
                "app_secretkey": "32254kUHE39AH3P90EQ"
            },
            "extra": {
                "extra1": "value1",
                "extra2": "value2",
                "extra3": "value3"
            }
        }
    
     */
    @SerializedName(value = "method")
    private String methodName;

    @SerializedName(value = "credentials")
    private Credentials credentials;

    @SerializedName(value = "params")
    private Params params;

    @SerializedName(value = "extra")
    private Extra extra;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public class Params {

        @SerializedName(value = "telesola_account")
        private String telesolaAccount;

        @SerializedName(value = "mac_address")
        private String macAddress;

        @SerializedName(value = "commercial_status")
        private String commercialStatus;

        @SerializedName(value = "contract_date")
        private String contractDate;

        @SerializedName(value = "contract_price")
        private String contractPrice;

        @SerializedName(value = "deposit_amount")
        private String depositAmount;

        @SerializedName(value = "contract_period")
        private String contractPeriod;

        @SerializedName(value = "installment_frequency")
        private String installmentFrequency;

        @SerializedName(value = "installment_day")
        private String installmentDay;

        @SerializedName(value = "momo_account")
        private String momoAccount;

        @SerializedName(value = "app_secretkey")
        private String appKey;

        public String getTelesolaAccount() {
            return telesolaAccount.trim().toUpperCase();
        }

        public void setTelesolaAccount(String telesolaAccount) {
            this.telesolaAccount = telesolaAccount;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
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

        public String getContractPeriod() {
            return contractPeriod;
        }

        public void setContractPeriod(String contractPeriod) {
            this.contractPeriod = contractPeriod;
        }

        public String getDepositAmount() {
            return depositAmount;
        }

        public void setDepositAmount(String depositAmount) {
            this.depositAmount = depositAmount;
        }
    }

    public class Extra {

        @SerializedName(value = "extra1")
        private String extra1;

        @SerializedName(value = "extra2")
        private String extra2;

        @SerializedName(value = "extra3")
        private String extra3;

        public String getExtra1() {
            return extra1;
        }

        public void setExtra1(String extra1) {
            this.extra1 = extra1;
        }

        public String getExtra2() {
            return extra2;
        }

        public void setExtra2(String extra2) {
            this.extra2 = extra2;
        }

        public String getExtra3() {
            return extra3;
        }

        public void setExtra3(String extra3) {
            this.extra3 = extra3;
        }
    }

    public class Credentials {

        @SerializedName(value = "user_account")
        private String userAccount;

        @SerializedName(value = "user_key")
        private String userKey;

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserKey() {
            return userKey;
        }

        public void setUserKey(String userKey) {
            this.userKey = userKey;
        }
    }
}
