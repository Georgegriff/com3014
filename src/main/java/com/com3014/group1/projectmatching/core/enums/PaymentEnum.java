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

    /**
     * The enumerator value
     */
    private String value = null;

    /**
     * Private constructor to avoid instantiation from outside the class
     *
     * @param value The value of the enumerator
     */
    private PaymentEnum(String value) {
        this.value = value;
    }

    /**
     * Get the value of the enumerator
     *
     * @return The value of the enumerator
     */
    public String getValue() {
        return this.value;
    }
}
