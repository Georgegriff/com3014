/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectDAO;
import com.com3014.group1.projectmatching.dao.ProjectInterestDAO;
import com.com3014.group1.projectmatching.dao.ProjectRoleDAO;
import com.com3014.group1.projectmatching.dao.RoleDAO;
import com.com3014.group1.projectmatching.dao.RoleQualificationDAO;
import com.com3014.group1.projectmatching.dao.RoleSkillDAO;
import com.com3014.group1.projectmatching.dto.Role;
import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.RoleQualification;
import com.com3014.group1.projectmatching.model.Skill;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
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

    private Role convertEntitiesToRoles(RoleEntity entity) {
        Role role = null;
        if (entity != null) {
            List<Skill> skillList = roleSkillDAO.findByRole(entity);
            List<RoleQualification> qualificationList = roleQualificationDAO.findByRole(entity);
            role = new Role(entity, skillList, qualificationList);
        }
        return role;
    }

    public Role findRoleById(int roleId) {
        RoleEntity roleEntity = roleDAO.findOne(roleId);
        return convertEntitiesToRoles(roleEntity);
    }
}
