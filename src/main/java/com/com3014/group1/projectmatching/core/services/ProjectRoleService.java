/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.RoleDAO;
import com.com3014.group1.projectmatching.dao.RoleQualificationDAO;
import com.com3014.group1.projectmatching.dao.RoleSkillDAO;
import com.com3014.group1.projectmatching.dto.Role;
import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.RoleQualification;
import com.com3014.group1.projectmatching.model.RoleSkill;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ggriffiths
 */
@Service
public class ProjectRoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private RoleSkillDAO roleSkillDAO;

    @Autowired
    private RoleQualificationDAO roleQualificationDAO;

    public Role convertEntitiesToRoles(RoleEntity entity) {
        Role role = null;
        if (entity != null) {
            List<RoleSkill> roleSkills = roleSkillDAO.findByRole(entity);
            List<RoleQualification> qualificationList = roleQualificationDAO.findByRole(entity);
            role = new Role(entity, roleSkills, qualificationList);
        }
        return role;
    }
    
    public RoleEntity convertRoleToEntity(Role role) {
        
        RoleEntity entity = this.roleDAO.findOne(role.getRoleId());
        
        if(entity == null) {
            entity = new RoleEntity();
        }
        entity.setName(role.getName());
        entity.setPayment(role.getPayment());
        return entity;
    }

    public Role findRoleById(int roleId) {
        RoleEntity roleEntity = roleDAO.findOne(roleId);
        return convertEntitiesToRoles(roleEntity);
    }
}
