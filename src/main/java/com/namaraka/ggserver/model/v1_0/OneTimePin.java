package com.namaraka.ggserver.model.v1_0;

import com.namaraka.ggserver.utils.Auditable;
import com.namaraka.ggserver.utils.DBMSXMLObject;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@Table(name = "otp")

public class OneTimePin extends BaseModel implements Auditable, Serializable {

    private static final long serialVersionUID = -4718360373948530546L;

    @Column(nullable = false)
    private String telesolaAccount;

    private String generatorId;

    private int otp;

    public String getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(String value) {
        this.generatorId = value;
    }

    public boolean isSetCustomermsisdn() {
        return (this.generatorId != null);
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getTelesolaAccount() {
        return telesolaAccount;
    }

    public void setTelesolaAccount(String telesolaAccount) {
        this.telesolaAccount = telesolaAccount;
    }

    @Override
    public String getUsername() {
        return "Hardcoded user";
    }

    @Override
    public DBMSXMLObject getXMLObject() {
        return this;
    }

}
