package com.namaraka.ggserver.model.v1_0;

import com.namaraka.ggserver.utils.Auditable;
import com.namaraka.ggserver.utils.DBMSXMLObject;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime;

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
@Table(name = "credentials")

public class SystemCredentials extends BaseModel implements Auditable, Serializable {

    private static final long serialVersionUID = 3746209030139459631L;

    @Column(nullable = false)
    private String generatorId;

    @Column(nullable = false)
    private String userAccount; //this can be either the telesola_account or the distributor_id or some other account

    @Column(nullable = false)
    private String password;

    @Transient
    private String typename;
    private String version;

    /**
     * Gets the value of the userAccount property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * Sets the value of the userAccount property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUserAccount(String value) {
        this.userAccount = value;
    }

    public boolean isSetTelesolaAccount() {
        return (this.getUserAccount() != null);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
