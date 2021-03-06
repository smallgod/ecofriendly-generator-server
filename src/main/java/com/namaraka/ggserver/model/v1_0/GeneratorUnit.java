package com.namaraka.ggserver.model.v1_0;

import com.namaraka.ggserver.constant.CommercialStatus;
import com.namaraka.ggserver.constant.ClientType;
import com.namaraka.ggserver.constant.InstallmentFrequency;
import com.namaraka.ggserver.constant.PaymentProgress;
import com.namaraka.ggserver.utils.Auditable;
import com.namaraka.ggserver.utils.DBMSXMLObject;
import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import jaxb.com.namaraka.ggserver.config.v1_0.Credentialstype;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDate;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime;
import org.joda.time.LocalDateTime;

@TypeDefs({
    @TypeDef(name = "jodalocaldatetime", typeClass = PersistentLocalDateTime.class,
            parameters = {
                @Parameter(value = "UTC", name = "databaseZone"),
                @Parameter(value = "UTC", name = "javaZone")
            }
    ),
    @TypeDef(name = "jodalocaldate", typeClass = PersistentLocalDate.class,
            parameters = {
                @Parameter(value = "UTC", name = "databaseZone"),
                @Parameter(value = "UTC", name = "javaZone")
            }
    )
})

@Entity
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@Table(name = "generator", uniqueConstraints = @UniqueConstraint(columnNames = {"generatorId", "macAddress"}))

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
    private ClientType registeredTo;

    @Type(type = "jodalocaldatetime")
    private LocalDateTime contractDate;

    private int enableDurationDefault; //set it at account setup

    @Column(nullable = false)
    private int remainingDays = 0;  //Number of days remaining to next installment payment

    private boolean isDepositAmountPaid; //shows if the client has paid the initial deposit amount for this generator

    @Type(type = "jodalocaldatetime")
    private LocalDateTime nextPaymentDueDate;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "retail_price")),
        @AttributeOverride(name = "currencycode", column = @Column(name = "cp_currencycode"))
    })
    private Amounttype retailPrice;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "depositAmount")),
        @AttributeOverride(name = "currencycode", column = @Column(name = "da_currencycode"))
    })
    private Amounttype depositAmount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "installmentAmount")),
        @AttributeOverride(name = "currencycode", column = @Column(name = "ia_currencycode"))
    })
    private Amounttype installmentAmount;

    @Column(name = "repayment_period_months", nullable = false)
    private int repaymentPeriod;

    @Enumerated(EnumType.STRING)
    private InstallmentFrequency installmentFrequency;

    //@Enumerated(EnumType.STRING)
    //private InstallmentDay installmentDay;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "outstandingBalance")),
        @AttributeOverride(name = "currencycode", column = @Column(name = "ob_currencycode"))
    })
    private Amounttype outstandingBalance;

    private int totalNumOfInstallmentsSoFarPaid;

    private int totalNumOfInstallmentsToBePaid;

    @Enumerated(EnumType.STRING)
    private PaymentProgress paymentProgress;

    @Column(length = 10000) //might want to change this - what if the data is too long
    private String activationCodes;

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
     * Gets the value of the retailPrice property.
     *
     * @return possible object is {@link Amounttype }
     *
     */
    public Amounttype getRetailPrice() {
        return retailPrice;
    }

    /**
     * Sets the value of the retailPrice property.
     *
     * @param value allowed object is {@link Amounttype }
     *
     */
    public void setRetailPrice(Amounttype value) {
        this.retailPrice = value;
    }

    public boolean isSetAmount() {
        return (this.getRetailPrice() != null);
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

    public ClientType getRegisteredTo() {
        return registeredTo;
    }

    public void setRegisteredTo(ClientType registeredTo) {
        this.registeredTo = registeredTo;
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

    public int getTotalNumOfInstallmentsSoFarPaid() {
        return totalNumOfInstallmentsSoFarPaid;
    }

    public void setTotalNumOfInstallmentsSoFarPaid(int totalNumOfInstallmentsSoFarPaid) {
        this.totalNumOfInstallmentsSoFarPaid = totalNumOfInstallmentsSoFarPaid;
    }

    public int getTotalNumOfInstallmentsToBePaid() {
        return totalNumOfInstallmentsToBePaid;
    }

    public void setTotalNumOfInstallmentsToBePaid(int totalNumOfInstallmentsToBePaid) {
        this.totalNumOfInstallmentsToBePaid = totalNumOfInstallmentsToBePaid;
    }

    public int getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(int repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public Amounttype getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Amounttype depositAmount) {
        this.depositAmount = depositAmount;
    }

    @Override
    public String toString() {
        return "GeneratorUnit{" + "generatorId=" + generatorId + ", telesolaAccount=" + telesolaAccount + ", macAddress=" + macAddress + ", mobileMoneyAccount=" + mobileMoneyAccount + ", commercialStatus=" + commercialStatus + ", registeredTo=" + registeredTo + ", contractDate=" + contractDate + ", contractPrice=" + retailPrice.getAmount() + ", depositAmount=" + depositAmount.getAmount() + ", installmentAmount=" + installmentAmount.getAmount() + ", contractPeriod=" + repaymentPeriod + ", installmentFrequency=" + installmentFrequency  + ", outstandingBalance=" + outstandingBalance.getAmount() + ", numberOfInstallmentsPaid=" + totalNumOfInstallmentsSoFarPaid + ", totalNumberOfInstallmentsToBePaid=" + totalNumOfInstallmentsToBePaid + ", paymentProgress=" + paymentProgress + '}';
    }

    public int getEnableDurationDefault() {
        return enableDurationDefault;
    }

    public void setEnableDurationDefault(int enableDurationDefault) {
        this.enableDurationDefault = enableDurationDefault;
    }

    public String getActivationCodes() {
        return activationCodes;
    }

    public void setActivationCodes(String activationCodes) {
        this.activationCodes = activationCodes;
    }

    @Override
    public String getModifyAction() {
        return "modify action";
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public boolean isIsDepositAmountPaid() {
        return isDepositAmountPaid;
    }

    public void setIsDepositAmountPaid(boolean isDepositAmountPaid) {
        this.isDepositAmountPaid = isDepositAmountPaid;
    }

    public LocalDateTime getNextPaymentDueDate() {
        return nextPaymentDueDate;
    }

    public void setNextPaymentDueDate(LocalDateTime nextPaymentDueDate) {
        this.nextPaymentDueDate = nextPaymentDueDate;
    }
}
