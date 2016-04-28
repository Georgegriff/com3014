package com.com3014.group1.projectmatching.model;

import com.com3014.group1.projectmatching.core.enums.PaymentEnum;
import java.io.Serializable;
import java.util.Objects;

/**
 * Representation of a Payment
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

    /**
     * Overridden because a payment is equal if it's fields are equal, not
     * objects
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.paymentType);
        hash = 67 * hash + Float.floatToIntBits(this.amount);
        return hash;
    }

    /**
     * Overridden because a payment is equal if it's fields are equal, not
     * objects
     *
     * @param obj The object to compare this one to
     * @return Whether the given object's fields are the same as this one
     */
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
