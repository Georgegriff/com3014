/*
 * To change this license header, choose License Headers in ProjectEntity Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectDAO;
import com.com3014.group1.projectmatching.dao.ProjectInterestDAO;
import com.com3014.group1.projectmatching.dao.ProjectRoleDAO;
import com.com3014.group1.projectmatching.dao.RoleQualificationDAO;
import com.com3014.group1.projectmatching.dao.RoleSkillDAO;
import com.com3014.group1.projectmatching.dao.UserDAO;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.Role;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectInterest;
import com.com3014.group1.projectmatching.model.ProjectRole;
import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.RoleQualification;
import com.com3014.group1.projectmatching.model.RoleSkill;
import com.com3014.group1.projectmatching.model.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sam Waters
 * @author Dan Ashworth
 */
@Service
public class ProjectService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private ProjectRoleDAO projectRoleDAO;

    @Autowired
    private ProjectInterestDAO projectInterestDAO;
    
    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private RoleSkillDAO roleSkillDAO;

    @Autowired
    private RoleQualificationDAO roleQualificationDAO;

    public Project getProject(int id, boolean roles, boolean interests) {
        //TODO:: need to ensure project owner and accepted people can view project
        Project project = null;
        try {
            ProjectEntity entity = projectDAO.findOne(id);
            project = convertEntityToProject(entity);
            retrieveProjectData(entity, project, roles, interests);

        } catch (ObjectNotFoundException onf) {
            project = null;
        }
        return project;
    }

    public List<Project> getProjectsUserOwns(int userId) {

        List<Project> userProjects = new ArrayList<>();

        try {
            UserEntity user = userDAO.findOne(userId);
            List<ProjectEntity> projects = projectDAO.findByProjectOwner(user);

            for (int i = 0; i < projects.size(); i++) {
                Project project = convertEntityToProject(projects.get(i));
                userProjects.add(project);
            }
        } catch (ObjectNotFoundException onf) {
            userProjects = null;
        }
        return userProjects;
    }

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try {
            // Find all the users
            List<ProjectEntity> allProjects = projectDAO.findAll();
            for (ProjectEntity entity : allProjects) {
                Project project = convertEntityToProject(entity);
                retrieveProjectData(entity, project, true, true);
                projects.add(project);
            }

        } catch (ObjectNotFoundException onf) {
            projects = null;
        }
        return projects;
    }
    
    @Transactional
    public void createProject(int userId, Project project) {
        ProjectEntity projectEntity = convertProjectToEntity(project);
        UserEntity userEntity = userDAO.findById(userId);
        projectDAO.setProjectByProjectOwner(userEntity, projectEntity);
    }

    @Transactional
    public void updateProject(int userId, Project project) {
        ProjectEntity projectEntity = convertProjectToEntity(project);
        UserEntity userEntity = userDAO.findById(userId);
        projectDAO.setProjectByProjectOwner(userEntity, projectEntity);
    }
    
    public Project convertEntityToProject(ProjectEntity entity) throws ObjectNotFoundException {
        if (entity != null) {
            // Create Project DTO Object with project information only
            return new Project(entity);
        } else {
            throw new ObjectNotFoundException(entity, "Project Not Found");
        }
    }
    
    public Project convertEntityToProjectAllData(ProjectEntity entity) {
        if(entity != null) {
            Project project = new Project(entity);
            retrieveProjectData(entity, project, true, true);
            return project;
        }
        return null;
    }
    
    public List<Project> convertEntityListToProjectList(List<ProjectEntity> entities) {
        List<Project> projects = new ArrayList<>();
        for(ProjectEntity entity : entities) {
            try {
                Project project = convertEntityToProject(entity);
                retrieveProjectData(entity, project, true, true);
                projects.add(project);
            }
            catch(ObjectNotFoundException onf) {
                projects = null;
                break;
            }
        }
        return projects;
    }

    private List<Role> getProjectRoles(ProjectEntity entity) {
        // Get the project roles
        if (entity != null) {
            List<ProjectRole> projectRoles = projectRoleDAO.findByProject(entity);
            return convertEntitiesToRoles(projectRoles);
        } else {
            return new ArrayList<>();
        }

    }

    private List<ProjectInterest> getProjectInterests(ProjectEntity entity) {
        // Get the project interests
        if (entity != null) {
            return projectInterestDAO.findByProject(entity);
        } else {
            return new ArrayList<>();
        }
    }

    private void retrieveProjectData(ProjectEntity projectEntity, Project project, boolean roles, boolean interests) {
        // If param set and the role list is currently null, retrieve data
        if (roles && (project.getRolesList() == null)) {
            project.setRolesList(getProjectRoles(projectEntity));
        }
        // If param set and the interest list is currently null, retrieve data
        if (interests && (project.getInterestsList() == null)) {
            project.setInterestsList(getProjectInterests(projectEntity));
        }
    }

    private List<Role> convertEntitiesToRoles(List<ProjectRole> entityList) throws ObjectNotFoundException {
        List<Role> roleList = new ArrayList<>();
        if (entityList != null) {
            for (int i = 0; i < entityList.size(); i++) {
                RoleEntity entity = entityList.get(i).getRole();
                List<RoleSkill> skillList = roleSkillDAO.findByRole(entity);
                List<RoleQualification> qualificationList = roleQualificationDAO.findByRole(entity);
                Role role = new Role(entity, skillList, qualificationList);
                roleList.add(role);
            }
            return roleList;
        } else {
            throw new ObjectNotFoundException(ProjectRole.class, "Project Roles Not Found");
        }
    }
    
    public ProjectEntity convertProjectToEntity(Project project) {
        // Try and find a persisted object in the database
        ProjectEntity entity = projectDAO.findOne(project.getProjectId());
              
        if(entity == null) {
            entity = new ProjectEntity();
        }
        
        entity.setName(project.getName());
        entity.setDescription(project.getDescription());
        entity.setProjectStart(project.getProjectStart());
        entity.setEstimatedEnd(project.getEstimatedEnd());
        entity.setLocation(project.getLocation());
        entity.setProjectOwner(userDAO.findOne(project.getProjectOwner()));
        return entity;
    }
    
    public ProjectEntity findProjectById(int projectId) {
        return this.projectDAO.findOne(projectId);
    }

}
