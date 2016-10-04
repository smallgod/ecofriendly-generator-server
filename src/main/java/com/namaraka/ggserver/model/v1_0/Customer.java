package com.namaraka.ggserver.model.v1_0;

import com.namaraka.ggserver.constant.ClientPaymentStatus;
import com.namaraka.ggserver.constant.ValueStore;
import com.namaraka.ggserver.utils.Auditable;
import com.namaraka.ggserver.utils.DBMSXMLObject;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.joda.time.DateTime;

@Entity
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@Table(name = "customer")

public class Customer extends BaseModel implements Auditable, Serializable {

    private static final long serialVersionUID = -8402038189783325805L;

    private String userMsisdn;
    @Embedded
    private Name name = new Name(); //initialise this to do away with null pointers
    private String preferredDebitAccount;
    @Enumerated(EnumType.STRING)
    private ClientPaymentStatus clientPayStatus; //status of Client Payments, 'still paying installments', 'paid full upfront' ??
    @Embedded
    private Amounttype nextInstallmentAmount; //the amount the customer is paying next
    private DateTime creationDate;
    private DateTime installmentDate;
    private DateTime firstInstallmentDate;
    private DateTime finalInstallmentDate;
    @Column(length = 20000)
    private String customerDetails; // should be embeddable - village, town, etc
    @Column(length = 20000)
    private String unitDetais; // should be embeddable - solarKit, Battery, app included etc

    /**
     * Gets the value of the msisdn property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUserMsisdn() {
        return userMsisdn;
    }

    /**
     * Sets the value of the userMsisdn property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUserMsisdn(String value) {
        this.userMsisdn = value;
    }

    public boolean isSetCustomermsisdn() {
        return (this.userMsisdn != null);
    }

    public ClientPaymentStatus getClientPayStatus() {
        return clientPayStatus;
    }

    public void setClientPayStatus(ClientPaymentStatus clientPayStatus) {
        this.clientPayStatus = clientPayStatus;
    }

    public String getPreferredDebitAccount() {
        return preferredDebitAccount;
    }

    public void setPreferredDebitAccount(String preferredDebitAccount) {
        this.preferredDebitAccount = preferredDebitAccount;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Amounttype getNextInstallmentAmount() {
        return nextInstallmentAmount;
    }

    public void setNextInstallmentAmount(Amounttype nextInstallmentAmount) {
        this.nextInstallmentAmount = nextInstallmentAmount;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateTime getInstallmentDate() {
        return installmentDate;
    }

    public void setInstallmentDate(DateTime installmentDate) {
        this.installmentDate = installmentDate;
    }

    public DateTime getFirstInstallmentDate() {
        return firstInstallmentDate;
    }

    public void setFirstInstallmentDate(DateTime firstInstallmentDate) {
        this.firstInstallmentDate = firstInstallmentDate;
    }

    public DateTime getFinalInstallmentDate() {
        return finalInstallmentDate;
    }

    public void setFinalInstallmentDate(DateTime finalInstallmentDate) {
        this.finalInstallmentDate = finalInstallmentDate;
    }

    public String getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(String customerDetails) {
        this.customerDetails = customerDetails;
    }

    public String getUnitDetais() {
        return unitDetais;
    }

    public void setUnitDetais(String unitDetais) {
        this.unitDetais = unitDetais;
    }

    @Override
    public DBMSXMLObject getXMLObject() {
        return this;
    }

    @Override
    public String getUsername() {
        return "hardcoded username";
    }

}
