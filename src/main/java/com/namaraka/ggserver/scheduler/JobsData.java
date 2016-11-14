/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.scheduler;

import com.namaraka.ggserver.httpmanager.HttpClientPool;
import com.namaraka.ggserver.interfaces.SharedAppConfigIF;

/**
 *
 * @author smallgod
 */
public class JobsData {

    private HttpClientPool httpClientPool;

    private String triggerName;
    private String jobName;
    private String groupName;
    private int repeatInterval;

    /**
     * 
     * @param httpClientPool
     * @param triggerName
     * @param jobName
     * @param groupName
     * @param repeatInterval 
     */
    public JobsData(HttpClientPool httpClientPool, String triggerName, String jobName, String groupName, int repeatInterval) {
        
        this.httpClientPool = httpClientPool;
        this.triggerName = triggerName;
        this.jobName = jobName;
        this.groupName = groupName;
        this.repeatInterval = repeatInterval;
    }
    

    public HttpClientPool getHttpClientPool() {
        return httpClientPool;
    }

    public void setHttpClientPool(HttpClientPool httpClientPool) {
        this.httpClientPool = httpClientPool;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }
}
