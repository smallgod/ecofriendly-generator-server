package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class ClientRegistrationRequest {

    /*
    JSON Request sample:

    {
        "method": "REGISTER_CLIENT",

        "credentials": {
            "user_account": "DKLA84009",
            "user_key": "5644777"
        },

        "params": {
            "first_name": "Davies",
            "second_name": "Mugume",
            "primary_phone": "256774983602",
            "other_phone": "256790790491",
            "email_address": "smallg@gmail.com",
            "physical_address": "Kisasi,Kyanja,Plot55,KisasiRoad",
            "client_type": "CUSTOMER",
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

        @SerializedName(value = "first_name")
        private String firstName;

        @SerializedName(value = "second_name")
        private String secondName;

        @SerializedName(value = "primary_phone")
        private String primaryPhone;

        @SerializedName(value = "other_phone")
        private String otherPhone;

        @SerializedName(value = "email_address")
        private String emailAddress;

        @SerializedName(value = "physical_address")
        private String physicalAddress;

        @SerializedName(value = "client_type")
        private String clientType;

        @SerializedName(value = "app_secretkey")
        private String appKey;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSecondName() {
            return secondName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }

        public String getPrimaryPhone() {
            return primaryPhone;
        }

        public void setPrimaryPhone(String primaryPhone) {
            this.primaryPhone = primaryPhone;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getOtherPhone() {
            return otherPhone;
        }

        public void setOtherPhone(String otherPhone) {
            this.otherPhone = otherPhone;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getPhysicalAddress() {
            return physicalAddress;
        }

        public void setPhysicalAddress(String physicalAddress) {
            this.physicalAddress = physicalAddress;
        }

        public String getClientType() {
            return clientType;
        }

        public void setClientType(String clientType) {
            this.clientType = clientType;
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
