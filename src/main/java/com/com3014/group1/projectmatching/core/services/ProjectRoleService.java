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
 * Service to provide conversions and saving of Project Roles
 *
 * @author George
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

    /**
     * Convert a ProjectRoleEntity into a ProjectRole
     *
     * @param entity The ProjectRoleEntity
     * @return The ProjectRole
     */
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

    /**
     * Convert a ProjectRole into a ProjectRoleEntity
     *
     * @param projectRole The ProjectRole
     * @return The ProjectRoleEntity
     */
    public ProjectRoleEntity convertRoleToEntity(ProjectRole projectRole) {
        ProjectRoleEntity entity = projectRoleDAO.findOne(projectRole.getProject().getProjectId());

        if (entity == null) {
            entity = new ProjectRoleEntity();
        }

        ProjectEntity projectEntity = projectService.convertProjectToEntity(projectRole.getProject());
        entity.setProject(projectEntity);
        entity.setRole(projectRole.getRole());

        return entity;
    }

    /**
     * Find a ProjectRole given the Project ID and Role ID
     *
     * @param projectId The ID of the Project
     * @param roleId The ID of the Role
     * @return The Project Role
     */
    public ProjectRole findByProjectRole(int projectId, int roleId) {
        ProjectRoleEntity entity = this.projectRoleDAO.findByProject_ProjectIdAndRole_RoleId(projectId, roleId);
        return convertEntityToRole(entity);
    }

    /**
     * Save the Project Roles to the database
     *
     * @param projectRoles The Project Roles to save
     */
    @Transactional
    public void saveProjectRoles(List<ProjectRole> projectRoles) {
        List<ProjectRoleEntity> projectRoleEntities = new ArrayList<>();
        List<RoleSkill> roleSkillEntities = new ArrayList<>();
        List<RoleQualification> roleQualiticationEntities = new ArrayList<>();

        for (ProjectRole projectRole : projectRoles) {
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
