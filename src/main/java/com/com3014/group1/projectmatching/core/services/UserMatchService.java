/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectApprovedDAO;
import com.com3014.group1.projectmatching.dao.ProjectDeclinedDAO;
import com.com3014.group1.projectmatching.dao.RoleDAO;
import com.com3014.group1.projectmatching.dao.UserMatchDAO;
import com.com3014.group1.projectmatching.dao.UserSetDAO;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.ProjectRoleMatch;
import com.com3014.group1.projectmatching.dto.Role;
import com.com3014.group1.projectmatching.model.ProjectApproved;
import com.com3014.group1.projectmatching.model.ProjectDeclined;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserMatch;
import com.com3014.group1.projectmatching.model.UserSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel
 */
@Service
public class UserMatchService {
    
    @Autowired
    private UserMatchDAO userMatchDAO;
    
    @Autowired
    private UserSetDAO  userSetDAO;
    
    @Autowired
    private ProjectApprovedDAO projectApprovedDAO;
    
    @Autowired
    private ProjectDeclinedDAO projectDeclinedDAO;
    
    @Autowired
    private ProjectRoleService projectRoleService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleDAO roleDAO;
    
    /* This method is public as according to the docs to properly implement transactional 
     * it needs to be public for hibernate to proxy the method as a transaction
     */
    @Transactional
    public void saveMatchesForUser(UserEntity userEntity, List<ProjectRoleMatch> projectRoles) {
               
        // Try to get the user match row from the db
        UserMatch userMatchEntity = this.userMatchDAO.findByUser(userEntity);
        // If there isn't one create a new one
        if(userMatchEntity == null) {
            userMatchEntity = new UserMatch();
        }
        // Set relevant params        
        userMatchEntity.setUser(userEntity);
        userMatchEntity.setCacheExpire(setCacheExpireTime());
        userMatchEntity.setStatusControl("C");
        
        this.userMatchDAO.save(userMatchEntity);
        // Remove previous set from database before adding new ones
        userSetDAO.deleteBySet(userMatchEntity);
        
        List<UserSet> userSets = new ArrayList<>();
        // Loop over all the projects the user matches too and save to the database
        for(int i = 0; i < projectRoles.size(); i++) {
            ProjectRoleMatch projectRole = projectRoles.get(i);
            
            UserSet userSetEntity = new UserSet();
            userSetEntity.setSet(userMatchEntity);
            userSetEntity.setProject(projectService.convertProjectToEntity(projectRole.getProject()));
            userSetEntity.setRole(this.projectRoleService.convertRoleToEntity(projectRole.getRole()));
            
            userSets.add(userSetEntity);
            //userSetDAO.save(userSetEntity);
        }
        //Bulk insert
        this.userSetDAO.save(userSets);
    }
    
    public List<Project> retrieveCachedMatches(UserMatch userMatch, int userId){
        List<UserSet> matches = this.userSetDAO.findBySet(userMatch);
        List<ProjectRoleMatch> projectRoles = new ArrayList<>();
        List<Project> projects = new ArrayList<>();
        
        for(UserSet match : matches) {
            Project projectMatch = this.projectService.convertEntityToProjectAllData(match.getProject());
            Role roleMatch = this.projectRoleService.convertEntitiesToRoles(match.getRole());
            projectRoles.add(new ProjectRoleMatch(projectMatch, roleMatch));
        }
        
        // Get the accepted and rejected users for this project
        List<ProjectRoleMatch> swiped = getAlreadySwipedProjects(userId);
        // Remove all users from the users list that are also in the swiped list
        projectRoles.removeAll(swiped);
        
        //Get the projects out that have not already been swiped
        for(ProjectRoleMatch projectRole : projectRoles) {
            projects.add(projectRole.getProject());
        }
        return projects;
    }
    
    public List<ProjectRoleMatch> getAlreadySwipedProjects(int userId) {
        List<ProjectApproved> approved = this.projectApprovedDAO.findByUser_UserId(userId);
        List<ProjectDeclined> declined = this.projectDeclinedDAO.findByUser_UserId(userId);
        
        List<ProjectRoleMatch> combined = new ArrayList<>();
        
        for(ProjectApproved project : approved) {
            Project projectMatch = this.projectService.convertEntityToProjectAllData(project.getProject());
            Role roleMatch = this.projectRoleService.convertEntitiesToRoles(project.getRole());
            combined.add(new ProjectRoleMatch(projectMatch, roleMatch));
        }
        for(ProjectDeclined project : declined) {
            Project projectMatch = this.projectService.convertEntityToProjectAllData(project.getProject());
            Role roleMatch = this.projectRoleService.convertEntitiesToRoles(project.getRole());
            combined.add(new ProjectRoleMatch(projectMatch, roleMatch));
        }
        return combined;
    }
    
    public UserMatch findMatchForUser(UserEntity entity) {
        return this.userMatchDAO.findByUser(entity);
    }
    
    public List<ProjectApproved> getUsersMatchedToProject(ProjectEntity project) {
        return projectApprovedDAO.findByProject(project);
    }
      
    private Date setCacheExpireTime() {
        Date date = new Date();
        Calendar c = Calendar.getInstance(); 
        c.setTime(date); 
        // Add one day to the current date as the cache expire time
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return date;
    }
    
    @Transactional
    public void saveAcceptedProjects(int userId, JSONArray acceptedProjects) {
        List<ProjectApproved> projectsApproved = new ArrayList<>();
        
        for(int i = 0; i < acceptedProjects.length(); i++) {
            JSONObject projectRole = acceptedProjects.getJSONObject(i);
            
            ProjectEntity projectEntity = this.projectService.findProjectById(projectRole.getInt("project"));
            RoleEntity roleEntity = this.roleDAO.findOne(projectRole.getInt("role"));
            UserEntity userEntity = this.userService.findEntityById(userId);
            
            ProjectApproved approvedEntity = new ProjectApproved();
            approvedEntity.setProject(projectEntity);
            approvedEntity.setUser(userEntity);
            approvedEntity.setRole(roleEntity);
            
            projectsApproved.add(approvedEntity);
        }
        this.projectApprovedDAO.save(projectsApproved);
    }
    
    @Transactional
    public void saveRejectedProjects(int userId, JSONArray rejectedProjects) {
        List<ProjectDeclined> projectsRejected = new ArrayList<>();
        
        for(int i = 0; i < rejectedProjects.length(); i++) {
            JSONObject projectRole = rejectedProjects.getJSONObject(i);
            
            ProjectEntity projectEntity = this.projectService.findProjectById(projectRole.getInt("project"));
            UserEntity userEntity = this.userService.findEntityById(userId);
            RoleEntity roleEntity = this.roleDAO.findOne(projectRole.getInt("role"));
            
            ProjectDeclined declinedEntity = new ProjectDeclined();
            declinedEntity.setProject(projectEntity);
            declinedEntity.setUser(userEntity);
            declinedEntity.setRole(roleEntity);
            
            projectsRejected.add(declinedEntity);
        }
        this.projectDeclinedDAO.save(projectsRejected);
    }      
}
