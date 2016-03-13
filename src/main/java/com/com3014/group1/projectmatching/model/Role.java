/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;

/**
 * A @Project role
 *
 * @author Sam Waters
 * @author Daniel Ashworth
 */
@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;
    
    @NotNull
    @Column (name = "name")
    private String name;
    
    @Type (type="com.com3014.group1.projectmatching.model.PaymentType")
    @Columns(columns = {
        @Column(name="payment"),
        @Column(name="payment_type")
        }
    )
    private Payment payment;

    public Role() {
    }
    
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
