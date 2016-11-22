package com.namaraka.ggserver.model.v1_0;

import com.namaraka.ggserver.constant.ClientStatus;
import com.namaraka.ggserver.constant.ClientType;
import com.namaraka.ggserver.utils.Auditable;
import com.namaraka.ggserver.utils.DBMSXMLObject;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@Table(name = "client", uniqueConstraints = @UniqueConstraint(columnNames = {"telesolaAccount", "primaryContactPhone"}))

public class Client extends BaseModel implements Auditable, Serializable {

    private static final long serialVersionUID = 1278328268329014986L;

    @Column(nullable = false)
    private String telesolaAccount;

    @Embedded
    private Name name = new Name(); //initialise this to do away with null pointers

    private String primaryContactPhone;

    private String otherContactPhone;

    private String email;

    @Column(length = 1000)
    private String physicalAddress; // should be embeddable remove the length attribute - village, town, etc

    private String registeredBy; //Telesola # of the registrar of this customer

    private int numberOfRegisteredUnits;

    /**
     * A client can be banned or suspended from ever getting another unit for
     * defaulting on payment or mishandling units (for case of distributors or
     * Telesola employees)
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientStatus clientStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    /**
     * Gets the value of the primaryContactPhone property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPrimaryContactPhone() {
        return primaryContactPhone;
    }

    /**
     * Sets the value of the primaryContactPhone property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPrimaryContactPhone(String value) {
        this.primaryContactPhone = value;
    }

    public boolean isSetCustomermsisdn() {
        return (this.primaryContactPhone != null);
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    @Override
    public DBMSXMLObject getXMLObject() {
        return this;
    }

    @Override
    public String getUsername() {

        return "To-Do, get username from telesola #";
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getTelesolaAccount() {
        return telesolaAccount;
    }

    public void setTelesolaAccount(String telesolaAccount) {
        this.telesolaAccount = telesolaAccount;
    }

    public String getOtherContactPhone() {
        return otherContactPhone;
    }

    public void setOtherContactPhone(String otherContactPhone) {
        this.otherContactPhone = otherContactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public int getNumberOfRegisteredUnits() {
        return numberOfRegisteredUnits;
    }

    public void setNumberOfRegisteredUnits(int numberOfRegisteredUnits) {
        this.numberOfRegisteredUnits = numberOfRegisteredUnits;
    }

    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }

    @Override
    public String getModifyAction() {
        return "modify action";
    }

}
