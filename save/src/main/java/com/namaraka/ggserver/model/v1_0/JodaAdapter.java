//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.01.11 at 05:50:15 PM EAT 
//


package com.namaraka.ggserver.model.v1_0;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.joda.time.DateTime;

public class JodaAdapter
    extends XmlAdapter<String, DateTime>
{


    @Override
    public DateTime unmarshal(String value) {
        return (com.namaraka.ggserver.utils.JodaDateTimeConverter.parseDate(value));
    }

    @Override
    public String marshal(DateTime value) {
        return (com.namaraka.ggserver.utils.JodaDateTimeConverter.printDate(value));
    }

}