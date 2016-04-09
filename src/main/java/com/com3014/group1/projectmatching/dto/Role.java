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
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.roleId);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.payment);
        hash = 23 * hash + Objects.hashCode(this.skillsList);
        hash = 23 * hash + Objects.hashCode(this.qualificationList);
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
        final Role other = (Role) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.roleId, other.roleId)) {
            return false;
        }
        if (!Objects.equals(this.payment, other.payment)) {
            return false;
        }
        if (!Objects.equals(this.skillsList, other.skillsList)) {
            return false;
        }
        if (!Objects.equals(this.qualificationList, other.qualificationList)) {
            return false;
        }
        return true;
    }
    
    
}
