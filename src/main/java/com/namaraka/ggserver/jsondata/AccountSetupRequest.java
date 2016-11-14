package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class AccountSetupRequest {

    /*
    
    JSON Request sample:
    
        {
            "method": "ACCOUNT_SETUP",
            "params": {
                "telesola_account": "786577309",
                "otp": "7865",
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

        @SerializedName(value = "otp")
        private String otp;

        @SerializedName(value = "app_secretkey")
        private String appKey;

        public String getTelesolaAccount() {
            return telesolaAccount.trim().toUpperCase();
        }

        public void setTelesolaAccount(String telesolaAccount) {
            this.telesolaAccount = telesolaAccount;
        }

        public String getOtp() {
            return otp.trim().toUpperCase();
        }

        public void setOtp(String otp) {
            this.otp = otp;
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
