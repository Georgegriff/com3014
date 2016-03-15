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
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.Role;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectInterest;
import com.com3014.group1.projectmatching.model.ProjectRole;
import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.RoleQualification;
import com.com3014.group1.projectmatching.model.RoleSkill;
import com.com3014.group1.projectmatching.model.Skill;
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
    private ProjectDAO projectDAO;

    @Autowired
    private ProjectRoleDAO projectRoleDAO;

    @Autowired
    private ProjectInterestDAO projectInterestDAO;

    @Autowired
    private RoleSkillDAO roleSkillDAO;

    @Autowired
    private RoleQualificationDAO roleQualificationDAO;

    public Project getProject(int id) {
        //TODO:: need to ensure project owner and accepted people can view project
        Project project;
        try {
            ProjectEntity entity = projectDAO.findOne(id);
            project = convertEntityToProject(entity);
        } catch (ObjectNotFoundException onf) {
            project = null;
        }
        return project;
    }

    public List<Project> getProjectsForUser(int userId) {
        // TODO: dummy implementation at the moment, returns all projects
        List<Project> userProjects = new ArrayList<>();

        try {
            List<ProjectEntity> projects = projectDAO.findAll();

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

    @Transactional
    public List<ProjectEntity> createProject(int userId, Project projectData) {
        //TODO::
        return null;
    }

    private Project convertEntityToProject(ProjectEntity entity) throws ObjectNotFoundException {
        // Get the list attributes of a project
        if (entity != null) {
            List<ProjectRole> projectRoles = projectRoleDAO.findByProject(entity);
            List<Role> roleList = convertEntitiesToRoles(projectRoles);
            List<ProjectInterest> projectInterest = projectInterestDAO.findByProject(entity);
            // Create Project DTO Object
            return new Project(entity, roleList, projectInterest);
        } else {
            throw new ObjectNotFoundException(entity, "Project Not Found");
        }
    }

    private List<Role> convertEntitiesToRoles(List<ProjectRole> entityList) throws ObjectNotFoundException {
        List<Role> roleList = new ArrayList<>();
        if (entityList != null) {
            for (int i = 0; i < entityList.size(); i++) {
                RoleEntity entity = entityList.get(i).getRole();
                List<RoleSkill> skillList = roleSkillDAO.findByRole(entity);
                List<Skill> skills = new ArrayList<Skill>();
                for (RoleSkill skill : skillList) {
                    skills.add(skill.getSkill());
                }
                List<RoleQualification> qualificationList = roleQualificationDAO.findByRole(entity);
                Role role = new Role(entity, skills, qualificationList);
                roleList.add(role);
            }
            return roleList;
        } else {
            throw new ObjectNotFoundException(ProjectRole.class, "Project Roles Not Found");
        }
    }

}
