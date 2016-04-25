/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectRoleDAO;
import com.com3014.group1.projectmatching.dao.RoleDAO;
import com.com3014.group1.projectmatching.dao.RoleQualificationDAO;
import com.com3014.group1.projectmatching.dao.RoleSkillDAO;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.ProjectRole;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectRoleEntity;
import com.com3014.group1.projectmatching.model.RoleQualification;
import com.com3014.group1.projectmatching.model.RoleSkill;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ggriffiths
 */
@Service
public class ProjectRoleService {

    @Autowired
    private ProjectRoleDAO projectRoleDAO;
    
    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private RoleSkillDAO roleSkillDAO;

    @Autowired
    private RoleQualificationDAO roleQualificationDAO;
    
    @Autowired
    private ProjectService projectService;
    
    public ProjectRole convertEntityToRole(ProjectRoleEntity entity) {
        ProjectRole projectRole = null;
        if (entity != null) {
            List<RoleSkill> roleSkills = roleSkillDAO.findByProjectRole(entity);
            List<RoleQualification> qualificationList = roleQualificationDAO.findByProjectRole(entity);
            Project project = projectService.convertEntityToProjectAllData(entity.getProject());
            
            projectRole = new ProjectRole(project, entity.getRole(), roleSkills, qualificationList);
        }
        return projectRole;
    }
    
    public ProjectRoleEntity convertRoleToEntity(ProjectRole projectRole) {
        ProjectRoleEntity entity = projectRoleDAO.findOne(projectRole.getProject().getProjectId());
        
        if(entity == null) {
            entity = new ProjectRoleEntity();
        }
        
        ProjectEntity projectEntity = projectService.convertProjectToEntity(projectRole.getProject());
        entity.setProject(projectEntity);
        entity.setRole(projectRole.getRole());
        
        return entity;
    }
    
    public ProjectRole findByProjectRole(int projectId, int roleId) {
        ProjectRoleEntity entity = this.projectRoleDAO.findByProject_ProjectIdAndRole_RoleId(projectId, roleId);
        return convertEntityToRole(entity);
    }
    
    @Transactional
    public void saveProjectRoles(List<ProjectRole> projectRoles) {
        List<ProjectRoleEntity> projectRoleEntities = new ArrayList<>();
        List<RoleSkill> roleSkillEntities = new ArrayList<>();
        List<RoleQualification> roleQualiticationEntities = new ArrayList<>();
        
        for(ProjectRole projectRole : projectRoles) {
            // Get all the project roles
            projectRoleEntities.add(convertRoleToEntity(projectRole));
            // Get all the role skills
            roleSkillEntities.addAll(projectRole.getSkillsList());
            // Get all the role qualifications
            roleQualiticationEntities.addAll(projectRole.getQualificationsList());
        }
        // Bulk saves
        projectRoleDAO.save(projectRoleEntities);
        roleSkillDAO.save(roleSkillEntities);
        roleQualificationDAO.save(roleQualiticationEntities);
    }
}
