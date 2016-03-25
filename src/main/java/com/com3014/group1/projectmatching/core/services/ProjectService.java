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
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectInterest;
import com.com3014.group1.projectmatching.model.ProjectRole;
import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.RoleQualification;
import com.com3014.group1.projectmatching.model.RoleSkill;
import com.com3014.group1.projectmatching.model.UserEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private RoleSkillDAO roleSkillDAO;

    @Autowired
    private RoleQualificationDAO roleQualificationDAO;

    public Project getProject(int id, boolean roles, boolean interests) {
        //TODO:: need to ensure project owner and accepted people can view project
        Project project = null;
        try {
            ProjectEntity entity = projectDAO.findOne(id);
            project = convertEntityToProject(entity);
            
            // If param set and the role list is currently null, retrieve data
            if(roles && (project.getRolesList() == null)) {
                project.setRolesList(getProjectRoles(entity));
            }
            // If param set and the interest list is currently null, retrieve data
            if(interests && (project.getInterestsList() == null)) {
                project.setInterestsList(getProjectInterests(entity));
            }
            
        } catch (ObjectNotFoundException onf) {
            project = null;
        } catch (NullPointerException npe) {
            // TODO: Need to consider what we do in this scenario
            project.setRolesList(new ArrayList<Role>());
            project.setInterestsList(new ArrayList<ProjectInterest>());
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
            onf.printStackTrace();
        }
        return userProjects;
    }
     
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<Project>();
        try {
            // Find all the users
            List<ProjectEntity> allProjects = projectDAO.findAll();
            for (int i = 0; i < allProjects.size(); i++) {
                Project project = convertEntityToProject(allProjects.get(i));
                projects.add(project);
            }
        } catch (ObjectNotFoundException onf) {
            projects = null;
            onf.printStackTrace();
        }
        return projects;
    }
    
    @Transactional
    public List<ProjectEntity> createProject(int userId, Project projectData) {
        //TODO::
        return null;
    }

    private Project convertEntityToProject(ProjectEntity entity) throws ObjectNotFoundException {
        // Get the list attributes of a project
        if (entity != null) {
            // Create Project DTO Object with project information only
            return new Project(entity);
        } else {
            throw new ObjectNotFoundException(entity, "Project Not Found");
        }
    }
    
    private List<Role> getProjectRoles(ProjectEntity entity) throws NullPointerException {
        // Get the roles and interest associated with a project
        if (entity != null) {           
            // Get the project roles
            List<ProjectRole> projectRoles = projectRoleDAO.findByProject(entity);
            return convertEntitiesToRoles(projectRoles);
        } else {
            throw new NullPointerException("Project entity cannot be null");
        }
    }
    
    private List<ProjectInterest> getProjectInterests(ProjectEntity entity) throws NullPointerException {
        if(entity != null) {
            // Get the project interests
            return projectInterestDAO.findByProject(entity); 
        } else {
            throw new NullPointerException("Project entity cannot be null");
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
}
