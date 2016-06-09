package com.namaraka.ggserver.model.v1_0;

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

@Entity
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@Table(name = "users")

public class User extends BaseModel implements Auditable, Serializable {

    private static final long serialVersionUID = -8402038189783325805L;

    @Embedded
    private Name name = new Name(); //initialise this to do away with null pointers

    @Enumerated(EnumType.STRING)
    private ValueStore preferredValueStore;

    private String preferredDebitAccount; //corresponding to preferred value-store

    private String userMsisdn;

    @Column(length = 20000)
    private String myMerchants; // in the form - handle to usergiven-name mapping {"nwsc":"maji", "umeme":"power", "katozrolex":"rolexguy", "greenbattries":"battries monthly"}

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

    
    /**
     * Gets the value of the myMerchants property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMyMerchants() {
        return myMerchants;
    }

    /**
     * Sets the value of the myMerchants property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMyMerchants(String value) {
        this.myMerchants = value;
    }

    public boolean isSetVersion() {
        return (this.myMerchants != null);
    }

    public ValueStore getPreferredValueStore() {
        return preferredValueStore;
    }

    public void setPreferredValueStore(ValueStore preferredValueStore) {
        this.preferredValueStore = preferredValueStore;
    }

    public String getPreferredDebitAccount() {
        return preferredDebitAccount;
    }

    public void setPreferredDebitAccount(String preferredDebitAccount) {
        this.preferredDebitAccount = preferredDebitAccount;
    }

    @Override
    public DBMSXMLObject getXMLObject() {
        return this;
    }

    @Override
    public String getUsername() {
        return "user name";
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

}
