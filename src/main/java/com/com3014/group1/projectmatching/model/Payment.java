/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

import com.com3014.group1.projectmatching.core.enums.PaymentEnum;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Sam Waters
 */
public class Payment implements Serializable {

    private PaymentEnum paymentType;
    private float amount;

    public Payment() {
    }

    public Payment(PaymentEnum paymentType, float amount) {
        this.paymentType = paymentType;
        this.amount = amount;
    }

    public PaymentEnum getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentEnum paymentType) {
        this.paymentType = paymentType;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.paymentType);
        hash = 67 * hash + Float.floatToIntBits(this.amount);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Payment other = (Payment) obj;
        if (Float.floatToIntBits(this.amount) != Float.floatToIntBits(other.amount)) {
            return false;
        }
        if (this.paymentType != other.paymentType) {
            return false;
        }
        return true;
    }
    
    
}
