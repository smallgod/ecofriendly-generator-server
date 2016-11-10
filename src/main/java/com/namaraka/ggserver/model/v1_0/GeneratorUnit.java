package com.namaraka.ggserver.model.v1_0;

import com.namaraka.ggserver.constant.CommercialStatus;
import com.namaraka.ggserver.constant.InPossession;
import com.namaraka.ggserver.constant.InstallmentDay;
import com.namaraka.ggserver.constant.InstallmentFrequency;
import com.namaraka.ggserver.constant.PaymentProgress;
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
import jaxb.com.namaraka.ggserver.config.v1_0.Credentialstype;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
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
@Table(name = "generator")

public class GeneratorUnit extends BaseModel implements Auditable, Serializable {

    private static final long serialVersionUID = -861018252025511918L;

    @Column(nullable = false)
    private String generatorId;

    @Column(nullable = false)
    private String telesolaAccount;

    @Column(nullable = false)
    private String macAddress;

    private String mobileMoneyAccount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommercialStatus commercialStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InPossession inPossession;

    @Type(type = "jodalocaldatetime")
    private LocalDateTime contractDate;

    @Embedded
    private Amounttype contractPrice;
    
    @Embedded
    private Amounttype depositAmount;
    
    @Column(name = "contractPeriod (months)", nullable = false)
    private String contractPeriod;

    @Embedded
    private Amounttype installmentAmount;

    @Enumerated(EnumType.STRING)
    private InstallmentFrequency installmentFrequency;

    @Enumerated(EnumType.STRING)
    private InstallmentDay installmentDay;
    
    @Embedded
    private Amounttype outstandingBalance;
    
    private int numberOfInstallmentsPaid;
    
    private int totalNumberOfInstallmentsToBePaid;

    @Enumerated(EnumType.STRING)
    private PaymentProgress paymentProgress;

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
     * Gets the value of the contractPrice property.
     *
     * @return possible object is {@link Amounttype }
     *
     */
    public Amounttype getContractPrice() {
        return contractPrice;
    }

    /**
     * Sets the value of the contractPrice property.
     *
     * @param value allowed object is {@link Amounttype }
     *
     */
    public void setContractPrice(Amounttype value) {
        this.contractPrice = value;
    }

    public boolean isSetAmount() {
        return (this.getContractPrice() != null);
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

    public boolean isSetTelesolaAccount() {
        return (this.getTelesolaAccount() != null);
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
     * @return the contractDate
     */
    public LocalDateTime getContractDate() {
        return contractDate;
    }

    /**
     * @param contractDate the contractDate to set
     */
    public void setContractDate(LocalDateTime contractDate) {
        this.contractDate = contractDate;
    }

    /**
     * @return the mobileMoneyAccount
     */
    public String getMobileMoneyAccount() {
        return mobileMoneyAccount;
    }

    /**
     * @param mobileMoneyAccount the mobileMoneyAccount to set
     */
    public void setMobileMoneyAccount(String mobileMoneyAccount) {
        this.mobileMoneyAccount = mobileMoneyAccount;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
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

    public CommercialStatus getCommercialStatus() {
        return commercialStatus;
    }

    public void setCommercialStatus(CommercialStatus commercialStatus) {
        this.commercialStatus = commercialStatus;
    }

    public InPossession getInPossession() {
        return inPossession;
    }

    public void setInPossession(InPossession inPossession) {
        this.inPossession = inPossession;
    }

    public Amounttype getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Amounttype installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public InstallmentFrequency getInstallmentFrequency() {
        return installmentFrequency;
    }

    public void setInstallmentFrequency(InstallmentFrequency installmentFrequency) {
        this.installmentFrequency = installmentFrequency;
    }

    public InstallmentDay getInstallmentDay() {
        return installmentDay;
    }

    public void setInstallmentDay(InstallmentDay installmentDay) {
        this.installmentDay = installmentDay;
    }

    public PaymentProgress getPaymentProgress() {
        return paymentProgress;
    }

    public void setPaymentProgress(PaymentProgress paymentProgress) {
        this.paymentProgress = paymentProgress;
    }

    public Amounttype getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(Amounttype outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public int getNumberOfInstallmentsPaid() {
        return numberOfInstallmentsPaid;
    }

    public void setNumberOfInstallmentsPaid(int numberOfInstallmentsPaid) {
        this.numberOfInstallmentsPaid = numberOfInstallmentsPaid;
    }

    public int getTotalNumberOfInstallmentsToBePaid() {
        return totalNumberOfInstallmentsToBePaid;
    }

    public void setTotalNumberOfInstallmentsToBePaid(int totalNumberOfInstallmentsToBePaid) {
        this.totalNumberOfInstallmentsToBePaid = totalNumberOfInstallmentsToBePaid;
    }

    public String getContractPeriod() {
        return contractPeriod;
    }

    public void setContractPeriod(String contractPeriod) {
        this.contractPeriod = contractPeriod;
    }

    public Amounttype getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Amounttype depositAmount) {
        this.depositAmount = depositAmount;
    }
}
