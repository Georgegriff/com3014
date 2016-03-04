/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

import com.com3014.group1.projectmatching.core.enums.PaymentEnum;

/**
 *
 * @author Sam Waters
 */
public class Payment {

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
}
