package com.namaraka.ggserver.jsondata;

import com.google.gson.annotations.SerializedName;

public class MakePaymentResponse {

    /*
    
    {
        ​"user_id"       : "0774983602",
        ​"invoice_number": "INV00003",
        ​"status"        : "PENDING",
        ​"cms_payment_id": "794001"
        ​"description"   : "under processing"

    }
    
     */
    @SerializedName(value = "user_id")
    private String userId;

    @SerializedName(value = "invoice_number")
    private String invoiceNumber;

    @SerializedName(value = "cms_payment_id")
    private String cmsPaymentId;

    @SerializedName(value = "status")
    private String status;

    @SerializedName(value = "description")
    private String statusDescription;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCmsPaymentId() {
        return cmsPaymentId;
    }

    public void setCmsPaymentId(String cmsPaymentId) {
        this.cmsPaymentId = cmsPaymentId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

}
