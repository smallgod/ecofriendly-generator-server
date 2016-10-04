package com.namaraka.ggserver.model.v1_0;

public class PaymentComplete {

    private static final long serialVersionUID = -1408718657632013689L;


    private String internalTransactionID; //generated internally
    private PaymentStatus paymentStatus;
    private String narration;
    private String acquistionID;


    public String getInternalTransactionID() {
        return internalTransactionID;
    }

    public void setInternalTransactionID(String internalTransactionID) {
        this.internalTransactionID = internalTransactionID;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getAcquistionID() {
        return acquistionID;
    }

    public void setAcquistionID(String acquistionID) {
        this.acquistionID = acquistionID;
    }


}
