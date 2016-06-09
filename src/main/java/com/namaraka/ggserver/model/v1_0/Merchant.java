package com.namaraka.ggserver.model.v1_0;


import com.namaraka.ggserver.constant.ValueStore;
import com.namaraka.ggserver.utils.Auditable;
import com.namaraka.ggserver.utils.DBMSXMLObject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

@Entity
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@Table(name = "merchants")

public class Merchant extends BaseModel implements Auditable, Serializable {

    @Enumerated(EnumType.STRING)
    private ValueStore merchantValueStore;

    private String merchantHandle;

    private String merchantName;

    private String merchantAccount;

    private String merchantMsisdn;

    private String merchantQueries; //each recipient has a set of questions the payer must (has to) answer

    private String identityAttributes; //these are all the attributes that can help us identify a merchant

    private String acquisitionID; //ID assigned when a request comes in with @new handle

    private LocalDateTime dateFullyAcquired;

    private long timeTakenToAcquire; //time taken between logging of new merchant acquisition request and date of full acquisition

    private String internalTransactionID; //foreign key from Payments table

    private String customerList;//JSon list

    private String merchantStatus;// get from userstatus - NEW|DORMANT|ACTIVE list

    @Transient
    private List<Map<String, Object>> customerListMapTransient;

    /**
     * Gets the value of the msisdn property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMerchantMsisdn() {
        return merchantMsisdn;
    }

    /**
     * Sets the value of the merchantMsisdn property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMerchantMsisdn(String value) {
        this.merchantMsisdn = value;
    }

    public boolean isSetCustomermsisdn() {
        return (this.merchantMsisdn != null);
    }

    /**
     * Gets the value of the identityAttributes property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIdentityAttributes() {
        return identityAttributes;
    }

    /**
     * Sets the value of the identityAttributes property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIdentityAttributes(String value) {
        this.identityAttributes = value;
    }

    public boolean isSetCustomeridentity() {
        return (this.identityAttributes != null);
    }

    /**
     * Gets the value of the merchantHandle property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMerchantHandle() {
        return merchantHandle;
    }

    /**
     * Sets the value of the merchantHandle property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMerchantHandle(String value) {
        this.merchantHandle = value;
    }

    public boolean isSetInvoicenumber() {
        return (this.merchantHandle != null);
    }

    /**
     * Gets the value of the merchantQueries property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMerchantQueries() {
        return merchantQueries;
    }

    /**
     * Sets the value of the merchantQueries property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMerchantQueries(String value) {
        this.merchantQueries = value;
    }

    public boolean isSetVersion() {
        return (this.merchantQueries != null);
    }

    public ValueStore getMerchantValueStore() {
        return merchantValueStore;
    }

    public void setMerchantValueStore(ValueStore merchantValueStore) {
        this.merchantValueStore = merchantValueStore;
    }

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    @Override
    public DBMSXMLObject getXMLObject() {
        return this;
    }

    @Override
    public String getUsername() {
        return "user name";
    }

    public String getAcquisitionID() {
        return acquisitionID;
    }

    public void setAcquisitionID(String acquisitionID) {
        this.acquisitionID = acquisitionID;
    }

    public LocalDateTime getDateFullyAcquired() {
        return dateFullyAcquired;
    }

    public void setDateFullyAcquired(LocalDateTime dateFullyAcquired) {
        this.dateFullyAcquired = dateFullyAcquired;
    }

    public long getTimeTakenToAcquire() {
        return timeTakenToAcquire;
    }

    public void setTimeTakenToAcquire(long timeTakenToAcquire) {
        this.timeTakenToAcquire = timeTakenToAcquire;
    }

////    public Payment getInitiatorPayment() {
////        return initiatorPayment;
////    }
////
////    public void setInitiatorPayment(Payment initiatorPayment) {
////        this.initiatorPayment = initiatorPayment;
////    }
    public String getInternalTransactionID() {
        return internalTransactionID;
    }

    public void setInternalTransactionID(String internalTransactionID) {
        this.internalTransactionID = internalTransactionID;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCustomerList() {
        return customerList;
    }

    public void setCustomerList(String customerList) {
        this.customerList = customerList;
    }

    public List<Map<String, Object>> getCustomerListMapTransient() {
        return customerListMapTransient;
    }

    public void setCustomerListMapTransient(List<Map<String, Object>> customerListMapTransient) {
        this.customerListMapTransient = customerListMapTransient;
    }

    public String getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(String merchantStatus) {
        this.merchantStatus = merchantStatus;
    }

}
