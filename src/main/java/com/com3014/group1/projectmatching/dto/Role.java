/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dto;

import com.com3014.group1.projectmatching.model.Payment;
import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.RoleQualification;
import com.com3014.group1.projectmatching.model.RoleSkill;
import java.util.List;

/**
 *
 * @author Daniel
 * @author Sam Waters
 */
public class Role {
    
    private Integer roleId;
    private String name;
    private Payment payment;
    private List<RoleSkill> skillsList;   
    private List<RoleQualification> qualificationList;
    
    public Role() {
        super();
    }
    
    public Role(RoleEntity entity, List<RoleSkill> skills, List<RoleQualification> qualifications) {
        super();
        this.roleId = entity.getRoleId();
        this.name = entity.getName();
        this.payment = entity.getPayment();
        this.skillsList = skills;
        this.qualificationList = qualifications;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
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

    public List<RoleSkill> getSkillsList() {
        return skillsList;
    }

    public void setSkillsList(List<RoleSkill> skillsList) {
        this.skillsList = skillsList;
    }

    public List<RoleQualification> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<RoleQualification> qualificationList) {
        this.qualificationList = qualificationList;
    }    
}
