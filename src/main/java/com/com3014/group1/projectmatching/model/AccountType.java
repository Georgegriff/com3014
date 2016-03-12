/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

/**
 *
 * @author George
 */
public enum AccountType {

    INTERNAL("INTERNAL"), LINKEDIN("LINKEDIN");
    private String type = null;

    private AccountType(String type) {
        this.type = type;
    }
    
    public String getType(){
        return type;
    }
}
