package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class PaymentStatusRequest {

    /*
    
    - warm clothing
    - bedsheets
    - towel
    - curtains
    - gloves/socks/head sock/sweater/jacket/official
    - umbrella
    
    JSON Request sample:
    
        {
            "method": "PAYMENT_STATUS",
            "params": {
                "telesola_account": "C774983602",
                "generatorId": "A00001",
                "activation_code": "45672674",
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

    public class Params {

        @SerializedName(value = "telesola_account")
        private String telesolaAccount;

        @SerializedName(value = "generator_id")
        private String generatorId;

        @SerializedName(value = "activation_code")
        private String activationCode;

        @SerializedName(value = "app_secretkey")
        private String appKey;

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

        public String getActivationCode() {
            return activationCode;
        }

        public void setActivationCode(String activationCode) {
            this.activationCode = activationCode;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
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
}
