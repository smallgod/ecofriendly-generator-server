package com.namaraka.ggserver.model.v1_0;

import com.namaraka.ggserver.constant.Status;
import com.namaraka.ggserver.utils.Auditable;
import com.namaraka.ggserver.utils.DBMSXMLObject;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.joda.time.DateTime;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime;
import org.joda.time.LocalDateTime;

@TypeDefs({
    @TypeDef(name = "jodalocaldatetime", typeClass = PersistentLocalDateTime.class,
            parameters = {
                @Parameter(value = "UTC", name = "databaseZone"),
                @Parameter(value = "UTC", name = "javaZone")
            }
    )
})

@Entity
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@Table(name = "payment")

public class MoMoPayment extends BaseModel implements Auditable, Serializable {

    private static final long serialVersionUID = 4420429742816294897L;

    @Column(nullable = false)
    private String telesolaAccount;
    
    @Column(nullable = false)
    private String generatorId;
    
    private String activationCode; //code that can activate the Unit
    
    private String paymentId;
    
    private String aggregatorTransID; //transaction ID issued by aggregator
    
    private String momoId; //ID issued by MNO after user confirms payment
    
    @Embedded
    private Amounttype amount; //initialise this to do away with null pointers
    
    private String debitAccount; //Mobile Money account to debit

    @Type(type = "jodalocaldatetime")
    private LocalDateTime approvalDate;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    private String statusDescription;
    
    private int enableDuration;
    
    @Embedded
    private Extensiontype extensionType;

    @Transient
    private String typename;
    private String version;

    /**
     * Gets the value of the extensionType property.
     *
     * @return possible object is {@link Credentialstype }
     *
     */
    public Extensiontype getExtensionType() {
        return extensionType;
    }

    /**
     * Sets the value of the extensionType property.
     *
     * @param value allowed object is {@link Credentialstype }
     *
     */
    public void setExtensionType(Extensiontype value) {
        this.extensionType = value;
    }

    public boolean isExtensionType() {
        return (this.getExtensionType() != null);
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
     * Gets the value of the telesolaAccount property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTelesolaAccount() {
        return telesolaAccount;
    }

    /**
     * Sets the value of the telesolaAccount property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTelesolaAccount(String value) {
        this.telesolaAccount = value;
    }

    public boolean isSetInvoiceNumber() {
        return (this.getTelesolaAccount() != null);
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
     * @return the approvalDate
     */
    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    /**
     * @param approvalDate the approvalDate to set
     */
    public void setApprovalDate(LocalDateTime approvalDate) {
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
     * @return the momoId
     */
    public String getMomoId() {
        return momoId;
    }

    /**
     * @param momoId the momoId to set
     */
    public void setMomoId(String momoId) {
        this.momoId = momoId;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
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

    public String getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(String generatorId) {
        this.generatorId = generatorId;
    }

    public int getEnableDuration() {
        return enableDuration;
    }

    public void setEnableDuration(int enableDuration) {
        this.enableDuration = enableDuration;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
