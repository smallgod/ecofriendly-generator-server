/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.interfaces;

import java.util.Map;

/**
 *
 * @author smallgod
 */
public interface RemoteRequest {

    public String getJsonUrl();
    
    public String getXmlUrl();

    public String getUserName();

    public String getPassWord();
    
    public String getUnitName();
    
    public Map<String, String> getHttpParams();
    
    public void setHttpParams(Map<String, String> httpParams);
    
}
