/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.model.v1_0;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime;
import org.joda.time.LocalDateTime;

/**
 *
 * @author smallgod
 */
@TypeDefs({
    @TypeDef(name = "jodalocaldatetime", typeClass = PersistentLocalDateTime.class,
            parameters = {
                @Parameter(value = "UTC", name = "databaseZone"),
                @Parameter(value = "UTC", name = "javaZone")
            }
    )
})
@MappedSuperclass
public class BaseModel implements Serializable {

    private static final long serialVersionUID = -9022906206290096352L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    private String createdBy;

    @Type(type = "jodalocaldatetime")
    private LocalDateTime createdOn;

    @Type(type = "jodalocaldatetime")
    private LocalDateTime dateLastModified;

    private String lastModifiedBy;

    private String lastModifyAction;

    private String dateModifiedHistory; // '|' separated strings

    private String modifiedByHistory; // '|' separated strings 

    private String modifyActionsHistory; // '|' separated strings    

    //private String description;
    /**
     * Get Id of the Object
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Set Id of the object
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {

        System.out.println(">>>>>>>>>>>>>>... >>>>>>>>>>>... CreatedON called!!");

        this.createdOn = LocalDateTime.now();
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(LocalDateTime dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    public String getDateModifiedHistory() {
        return dateModifiedHistory;
    }

    public void setDateModifiedHistory(String dateModifiedHistory) {
        this.dateModifiedHistory = dateModifiedHistory;
    }

    public String getModifiedByHistory() {
        return modifiedByHistory;
    }

    public void setModifiedByHistory(String modifiedByHistory) {
        this.modifiedByHistory = modifiedByHistory;
    }

    /*public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }*/
    public String getModifyActionsHistory() {
        return modifyActionsHistory;
    }

    public void setModifyActionsHistory(String modifyActionsHistory) {
        this.modifyActionsHistory = modifyActionsHistory;
    }

    public String getLastModifyAction() {
        return lastModifyAction;
    }

    public void setLastModifyAction(String lastModifyAction) {
        this.lastModifyAction = lastModifyAction;
    }
}
