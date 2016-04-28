package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectDAO;
import com.com3014.group1.projectmatching.dao.ProjectInterestDAO;
import com.com3014.group1.projectmatching.dao.ProjectRoleDAO;
import com.com3014.group1.projectmatching.dao.RoleQualificationDAO;
import com.com3014.group1.projectmatching.dao.RoleSkillDAO;
import com.com3014.group1.projectmatching.dao.UserDAO;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.ProjectRole;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectInterest;
import com.com3014.group1.projectmatching.model.ProjectRoleEntity;
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
 * A service to provide Project retrieval and saving to the REST Service
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

    @Autowired
    private ProjectRoleService projectRoleService;

    /**
     * Get the Project from the ID
     *
     * @param id The ID of the Project
     * @param user The Use that is the Project owner
     * @param roles Whether to get the roles
     * @param interests Whether to get the interests
     * @return The Project
     */
    public Project getProject(int id, User user, boolean roles, boolean interests) {
        Project project = null;
        try {
            ProjectEntity entity = projectDAO.findOne(id);
            // Check that the user is allowed to via this project
            if (entity.getProjectOwner().getUserId().equals(user.getUserId())) {
                project = convertEntityToProject(entity);
                retrieveProjectData(entity, project, roles, interests);
            }

        } catch (ObjectNotFoundException onf) {
            project = null;
        }
        return project;
    }

    /**
     * Get all of the Projects that the User owns
     * @param user The User
     * @return The list of Projects that the User owns
     */
    public List<Project> getProjectsUserOwns(User user) {

        List<Project> userProjects = new ArrayList<>();

        try {
            UserEntity entity = userDAO.findOne(user.getUserId());
            List<ProjectEntity> projects = projectDAO.findByProjectOwner(entity);

            for (int i = 0; i < projects.size(); i++) {
                Project project = convertEntityToProject(projects.get(i));
                userProjects.add(project);
            }
        } catch (ObjectNotFoundException onf) {
            userProjects = null;
        }
        return userProjects;
    }

    /**
     * Get all Projects within the system
     * @return All the Projects within the system
     */
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try {
            // Find all the projects
            List<ProjectEntity> allProjects = projectDAO.findAll();
            for (ProjectEntity entity : allProjects) {
                Project project = convertEntityToProjectAllData(entity);
                projects.add(project);
            }

        } catch (ObjectNotFoundException onf) {
            projects = null;
        }
        return projects;
    }

    /**
     * Save a Project to the database
     * @param project The Project to save
     * @return Whether the Project was saved
     */
    @Transactional
    public boolean saveProject(Project project) {
        if (!validProject(project)) {
            return false;
        }
        // Save the high level project
        projectDAO.save(convertProjectToEntity(project));
        // Save the project interest
        projectInterestDAO.save(project.getInterestsList());
        // Save the project roles, skills and qualifications
        projectRoleService.saveProjectRoles(project.getRolesList());

        return true;
    }

    /**
     * Check if the Project is valid
     * 
     * @param project The Project to check
     * @return Whether the Project is valid
     */
    private boolean validProject(Project project) {
        if (project.getName().equals("") || project.getName().isEmpty() || project.getName() == null) {
            return false;
        }
        if (project.getDescription().equals("") || project.getDescription().isEmpty() || project.getDescription() == null) {
            return false;
        }
        if (project.getProjectStart() == null || project.getEstimatedEnd() == null || project.getProjectStart().after(project.getEstimatedEnd())) {
            return false;
        }
        if (userDAO.findByUserId(project.getProjectId()) == null) {
            return false;
        }
        return true;
    }

    /**
     * Convert a ProjectEntity in to a Project
     * @param entity The ProjectEntity
     * @return The Project
     * @throws ObjectNotFoundException Exception thrown if the entity is null
     */
    public Project convertEntityToProject(ProjectEntity entity) throws ObjectNotFoundException {
        if (entity != null) {
            // Create Project DTO Object with project information only
            return new Project(entity);
        } else {
            throw new ObjectNotFoundException(entity, "Project Not Found");
        }
    }

    /**
     * Convert a Project into a ProjectEntity
     * 
     * @param entity The Project Entity
     * @return The Project
     */
    public Project convertEntityToProjectAllData(ProjectEntity entity) {
        if (entity != null) {
            Project project = new Project(entity);
            retrieveProjectData(entity, project, true, true);
            return project;
        }
        return null;
    }

    /**
     * Convert A list of Project Entities to a list of Projects
     * @param entities The list of Project Entities
     * @return The list of Projects
     */
    public List<Project> convertEntityListToProjectList(List<ProjectEntity> entities) {
        List<Project> projects = new ArrayList<>();
        for (ProjectEntity entity : entities) {
            try {
                Project project = convertEntityToProject(entity);
                retrieveProjectData(entity, project, true, true);
                projects.add(project);
            } catch (ObjectNotFoundException onf) {
                projects = null;
                break;
            }
        }
        return projects;
    }

    /**
     * Get the Project Roles for a Project Entity
     * @param entity The Project Entity
     * @return The list of Project Roles
     */
    private List<ProjectRole> getProjectRoles(ProjectEntity entity) {
        // Get the project roles
        if (entity != null) {
            List<ProjectRoleEntity> projectRoles = projectRoleDAO.findByProject(entity);
            return convertEntitiesToRoles(projectRoles);
        } else {
            return new ArrayList<>();
        }

    }

    /**
     * Get the Project Interests for a Project Entity
     * @param entity
     * @return 
     */
    private List<ProjectInterest> getProjectInterests(ProjectEntity entity) {
        // Get the project interests
        if (entity != null) {
            return projectInterestDAO.findByProject(entity);
        } else {
            return new ArrayList<>();
        }
    }

    private void retrieveProjectData(ProjectEntity projectEntity, Project project, boolean roles, boolean interests) {
        if (roles) {
            project.setRolesList(getProjectRoles(projectEntity));
        }
        if (interests) {
            project.setInterestsList(getProjectInterests(projectEntity));
        }
    }

    private List<ProjectRole> convertEntitiesToRoles(List<ProjectRoleEntity> entityList) throws ObjectNotFoundException {
        List<ProjectRole> projectRoleList = new ArrayList<>();
        if (entityList != null) {
            for (int i = 0; i < entityList.size(); i++) {
                ProjectRoleEntity projectRoleEntity = entityList.get(i);

                List<RoleSkill> skillList = roleSkillDAO.findByProjectRole(projectRoleEntity);
                List<RoleQualification> qualificationList = roleQualificationDAO.findByProjectRole(projectRoleEntity);
                Project project = convertEntityToProject(projectRoleEntity.getProject());

                ProjectRole projectRole = new ProjectRole(project, projectRoleEntity.getRole());
                projectRole.setQualificationsList(qualificationList);
                projectRole.setSkillsList(skillList);

                projectRoleList.add(projectRole);
            }
            return projectRoleList;
        } else {
            throw new ObjectNotFoundException(ProjectRoleEntity.class, "Project Roles Not Found");
        }
    }

    public ProjectEntity convertProjectToEntity(Project project) {
        // Try and find a persisted object in the database
        ProjectEntity entity = projectDAO.findOne(project.getProjectId());

        if (entity == null) {
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

    public void getRemainingData(Project project) {
        ProjectEntity projectEntity = projectDAO.findOne(project.getProjectId());
        retrieveProjectData(projectEntity, project, true, true);
    }

}
