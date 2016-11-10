package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class RequestForOTPRequest {

    /*
    
    JSON Request sample:
    
        {
            "method": "REQUEST_OTP",
            "params": {
                "telesola_account": "774983602",
                "app_secretkey": "32254kUHE39AH3P90EQ"
            },
            "extra": {
                "extra1": "value1",
                "extra2": "value2",
                "extra3": "value3"
            }
        }
    
        JSON Response sample:
    
        {
            "telesola_account": "7749",
            "primary_phone": "256774568901",
            "status": "SUCCESSFUL",
            "description": "One-time PIN sent to 256774568901 successfuly"
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

        @SerializedName(value = "app_secretkey")
        private String appKey;

        public String getTelesolaAccount() {
            return telesolaAccount;
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
