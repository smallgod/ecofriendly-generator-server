package com.namaraka.ggserver.model.v1_0;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@Table(name = "activations_tracker", uniqueConstraints = @UniqueConstraint(columnNames = {"activationCode"}))

public class ActivationCodeTracker implements Serializable {

    private static final long serialVersionUID = 7568430557483372104L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false)
    private String telesolaAccount;

    private String generatorId;

    private int activationCode;

    public String getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(String value) {
        this.generatorId = value;
    }

    public boolean isSetCustomermsisdn() {
        return (this.generatorId != null);
    }

    public int getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(int activationCode) {
        this.activationCode = activationCode;
    }

    public String getTelesolaAccount() {
        return telesolaAccount;
    }

    public void setTelesolaAccount(String telesolaAccount) {
        this.telesolaAccount = telesolaAccount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
