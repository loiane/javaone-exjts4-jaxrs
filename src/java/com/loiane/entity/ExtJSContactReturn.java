/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loiane.entity;

import java.util.List;

/**
 *
 * @author loiane
 */
public class ExtJSContactReturn {
    
    private String total;
    
    private List<Contact> data;
    
    private boolean success;

    public ExtJSContactReturn(String total, List<Contact> data, boolean success) {
        this.total = total;
        this.data = data;
        this.success = success;
    }

    public ExtJSContactReturn(){}
    
    /**
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return the data
     */
    public List<Contact> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<Contact> data) {
        this.data = data;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    
}
