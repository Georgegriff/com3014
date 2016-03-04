/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

import java.util.List;

/**
 * A @Project role
 *
 * @author Sam Waters
 */
public class Role {

    private int roleId;
    private String name;
    private Payment payment;
    private List<Skill> skills;
    private List<Qualification> qualifications;

    public Role() {
    }

    public Role(int roleId, String name, Payment payment, List<Skill> skills, List<Qualification> qualifications) {
        this.roleId = roleId;
        this.name = name;
        this.payment = payment;
        this.skills = skills;
        this.qualifications = qualifications;
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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

}
