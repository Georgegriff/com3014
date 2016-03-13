/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.enums;

/**
 * The type of payment offered
 *
 * @author Sam Waters
 */
public enum PaymentEnum {
    TBC("TBC"),
    HOURLY("HOURLY"),
    TOTAL("TOTAL"),
    FREE("FREE");
    
    private String value = null;
    
    PaymentEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}
