package com.namaraka.ggserver.model.v1_0;

import com.namaraka.ggserver.constant.StatusType;
import com.namaraka.ggserver.utils.Auditable;
import com.namaraka.ggserver.utils.DBMSXMLObject;
import java.io.Serializable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.joda.time.DateTime;

@Entity
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@Table(name = "transactions")

public class MoMoTransaction extends BaseModel implements Auditable, Serializable{

    private static final long serialVersionUID = 4420429742816294897L;

    private String customerMsisdn;
    private DateTime creationDate;
    private DateTime loggingDate;
    private DateTime approvalDate;
    private String invoiceNumber; //number issued by app to initiate payment
    private String aggregatorTransID; //transaction ID issued by aggregator
    private String recieptNumber; //ID issued by MNO after user confirms payment
    @Embedded private Amounttype amount; //initialise this to do away with null pointers
    private String payerNarration;
    private String debitAccount; //Mobile Money account to debit
    @Enumerated(EnumType.STRING) private StatusType status;
    private String statusDescription;
    private Extensiontype extensionType;
    
    @Transient private String typename;
    private String version;
    

    /**
     * Gets the value of the extensionType property.
     *
     * @return possible object is {@link Credentialstype }
     *
     */
    public Extensiontype getExtensionType() {
        return getExtensionType();
    }

    /**
     * Sets the value of the extensionType property.
     *
     * @param value allowed object is {@link Credentialstype }
     *
     */
    public void setExtensionType(Extensiontype value) {
        this.setExtensionType(value);
    }

    public boolean isExtensionType() {
        return (this.getExtensionType() != null);
    }


    /**
     * Gets the value of the msisdn property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCustomerMsisdn() {
        return customerMsisdn;
    }

    /**
     * Sets the value of the customerMsisdn property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCustomerMsisdn(String value) {
        this.customerMsisdn = value;
    }

    public boolean isSetCustomermsisdn() {
        return (this.getCustomerMsisdn() != null);
    }

    /**
     * Gets the value of the amount property.
     *
     * @return possible object is {@link Amounttype }
     *
     */
    public Amounttype getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     *
     * @param value allowed object is {@link Amounttype }
     *
     */
    public void setAmount(Amounttype value) {
        this.amount = value;
    }

    public boolean isSetAmount() {
        return (this.getAmount() != null);
    }

   
    /**
     * Gets the value of the payerNarration property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPayerNarration() {
        return payerNarration;
    }

    /**
     * Sets the value of the payerNarration property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPayerNarration(String value) {
        this.payerNarration = value;
    }

    public boolean isSetPaymentnarration() {
        return (this.getPayerNarration() != null);
    }

    /**
     * Gets the value of the debitAccount property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDebitAccount() {
        return debitAccount;
    }

    /**
     * Sets the value of the debitAccount property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDebitAccount(String value) {
        this.debitAccount = value;
    }

    public boolean isSetCustomeridentity() {
        return (this.getDebitAccount() != null);
    }

   

    /**
     * Gets the value of the invoiceNumber property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Sets the value of the invoiceNumber property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }

    public boolean isSetInvoiceNumber() {
        return (this.getInvoiceNumber() != null);
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is {@link Statustype }
     *
     */
    public StatusType getTransactionstatus() {
        return getStatus();
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is {@link Statustype }
     *
     */
    public void setTransactionstatus(StatusType value) {
        this.setStatus(value);
    }

    public boolean isSetTransactionstatus() {
        return (this.getStatus() != null);
    }

    /**
     * Gets the value of the statusDescription property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getStatusDescription() {
        return statusDescription;
    }

    /**
     * Sets the value of the statusDescription property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setStatusDescription(String value) {
        this.statusDescription = value;
    }

    public boolean isSetCollectionsaccount() {
        return (this.getStatusDescription() != null);
    }

    


    /**
     * Gets the value of the creationDate property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of the creationDate property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setCreationDate(DateTime value) {
        this.creationDate = value;
    }

    public boolean isSetClientpaymentdate() {
        return (this.getCreationDate() != null);
    }
    
    /**
     * Gets the value of the version property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setVersion(String value) {
        this.version = value;
    }

    public boolean isSetVersion() {
        return (this.getVersion() != null);
    }

    /**
     * Gets the value of the typename property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTypename() {
        if (typename == null) {
            return "servicerequest";
        } else {
            return typename;
        }
    }

    /**
     * Sets the value of the typename property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTypename(String value) {
        this.typename = value;
    }

    public boolean isSetTypename() {
        return (this.getTypename() != null);
    }
    
     /**
     * @return the loggingDate
     */
    public DateTime getLoggingDate() {
        return loggingDate;
    }

    /**
     * @param loggingDate the loggingDate to set
     */
    public void setLoggingDate(DateTime loggingDate) {
        this.loggingDate = loggingDate;
    }

    /**
     * @return the approvalDate
     */
    public DateTime getApprovalDate() {
        return approvalDate;
    }

    /**
     * @param approvalDate the approvalDate to set
     */
    public void setApprovalDate(DateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    /**
     * @return the aggregatorTransID
     */
    public String getAggregatorTransID() {
        return aggregatorTransID;
    }

    /**
     * @param aggregatorTransID the aggregatorTransID to set
     */
    public void setAggregatorTransID(String aggregatorTransID) {
        this.aggregatorTransID = aggregatorTransID;
    }

    /**
     * @return the recieptNumber
     */
    public String getRecieptNumber() {
        return recieptNumber;
    }

    /**
     * @param recieptNumber the recieptNumber to set
     */
    public void setRecieptNumber(String recieptNumber) {
        this.recieptNumber = recieptNumber;
    }

    /**
     * @return the status
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(StatusType status) {
        this.status = status;
    }

    

    @Override
    public DBMSXMLObject getXMLObject() {
        return this;
    }

    @Override
    public String getUsername() {
        return "hardcoded user";
    }

    // This ensures that a check is always performed when the model is saved, 
    //instead of having to manually add the check to every piece of code that saves the model.
//    @PrePersist
//    public void prepareToInsert() {//cant we do this check inside the Interceptor
//        List<Servicerequest> conflicts = find("user=?").fetch();
//        if (!conflicts.isEmpty()) {
//            throw new MyCustomException("username `" + name + "` already exists");
//        }
//    }

   

}
