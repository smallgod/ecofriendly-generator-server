//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.26 at 07:43:38 PM EAT 
//


package com.namaraka.ggserver.model.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for commonattributestype complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="commonattributestype"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="datecreated" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="createdby" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="datelastmodified" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="lastmodifiedby" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="datemodifiedhistory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="modifiedbyhistory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commonattributestype", propOrder = {
    "datecreated",
    "createdby",
    "datelastmodified",
    "lastmodifiedby",
    "datemodifiedhistory",
    "modifiedbyhistory",
    "description"
})
public class Commonattributestype {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datecreated;
    @XmlElement(required = true)
    protected String createdby;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datelastmodified;
    protected String lastmodifiedby;
    protected String datemodifiedhistory;
    protected String modifiedbyhistory;
    protected String description;

    /**
     * Gets the value of the datecreated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatecreated() {
        return datecreated;
    }

    /**
     * Sets the value of the datecreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatecreated(XMLGregorianCalendar value) {
        this.datecreated = value;
    }

    /**
     * Gets the value of the createdby property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedby() {
        return createdby;
    }

    /**
     * Sets the value of the createdby property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedby(String value) {
        this.createdby = value;
    }

    /**
     * Gets the value of the datelastmodified property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatelastmodified() {
        return datelastmodified;
    }

    /**
     * Sets the value of the datelastmodified property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatelastmodified(XMLGregorianCalendar value) {
        this.datelastmodified = value;
    }

    /**
     * Gets the value of the lastmodifiedby property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    /**
     * Sets the value of the lastmodifiedby property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastmodifiedby(String value) {
        this.lastmodifiedby = value;
    }

    /**
     * Gets the value of the datemodifiedhistory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatemodifiedhistory() {
        return datemodifiedhistory;
    }

    /**
     * Sets the value of the datemodifiedhistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatemodifiedhistory(String value) {
        this.datemodifiedhistory = value;
    }

    /**
     * Gets the value of the modifiedbyhistory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedbyhistory() {
        return modifiedbyhistory;
    }

    /**
     * Sets the value of the modifiedbyhistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedbyhistory(String value) {
        this.modifiedbyhistory = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
