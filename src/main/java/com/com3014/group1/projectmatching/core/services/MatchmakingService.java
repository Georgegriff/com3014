/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.Role;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.matchmaking.Matchmaking;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectMatch;
import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserMatch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author ggriffiths
 */
@Service
public class MatchmakingService {

    @Autowired
    private ProjectRoleService roleService;

    @Autowired
    private Matchmaking matching;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMatchService userMatchService;
    
    @Autowired
    private ProjectMatchService projectMatchService;
    
    @Autowired
    private ProjectService projectService;
        
    /**
     *
     * @param roleId
     * @param projectId
     * @return
     */
    public List<User> matchUserToRole(int projectId, int roleId) {
        Role role = roleService.findRoleById(roleId);
        ProjectEntity projectEntity = this.projectService.findProjectById(projectId);
        ProjectMatch projectMatch = this.projectMatchService.findMatchForProject(projectEntity);
        List<User> users = null;
        
        if(role == null) {
            return new ArrayList<>();
        }
        // If we dont have a current match entry and we have a valid role
        // or if the match set cache has expired then rerun the algorithm
        else if((projectMatch == null) || (projectMatch.getCacheExpire().before(new Date()))) {
            users = matching.findUsersForRole(role);
            this.projectMatchService.saveMatchesForProject(projectEntity, users);
        } 
        // Else, retrieve cached set
        else {
            users = this.projectMatchService.retrieveCachedMatches(projectMatch);
        }
        
        return users;
    }

    public List<Project> matchUserToProjectRoles(User user) {
        UserEntity userEntity = userService.convertUserToEntity(user);
        UserMatch userMatch = userMatchService.findMatchForUser(userEntity);
        List<Project> projects = null;
        // If there is no match in the database or the cache has expired, 
        // rerun the algorithm and save the new set
        if((userMatch == null) || (userMatch.getCacheExpire().before(new Date()))) {
            projects = matching.findRolesForUser(user);
            this.userMatchService.saveMatchesForUser(userEntity, projects);
        }
        // Else, retrieve the cached set
        else {
            projects = this.userMatchService.retrieveCachedMatches(userMatch);
        }
        return projects;
    }    
    
    public void saveProjectSwipePreferences(Integer userId, String accepted, String declined) {
        List<String> acceptedList = new ArrayList<>();
        List<String> rejectedList = new ArrayList<>();
        
        if(!accepted.equals("[]")) {
            // Temp measure to strip off array [] to form a CSV, think of better solution DA
            accepted = accepted.substring(1, accepted.length()-1);
            acceptedList = Arrays.asList(accepted.split(","));
        }
        else if(!declined.equals("[]")) {
            declined = declined.substring(1, declined.length()-1);
            rejectedList = Arrays.asList(declined.split(","));
        }            
        this.userMatchService.saveAcceptedProjects(userId, acceptedList);
        this.userMatchService.saveRejectedProjects(userId, rejectedList);
    }
    
    public void saveUserSwipePreferences(Integer projectId, String acceptedJSON, String declinedJSON) {
        List<String> acceptedList = new ArrayList<>();
        List<String> rejectedList = new ArrayList<>();
        
        if(!acceptedJSON.equals("[]")) {
            // Temp measure to strip off array [] to form a CSV, think of better solution DA
            acceptedJSON = acceptedJSON.substring(1, acceptedJSON.length()-1);
            acceptedList = Arrays.asList(acceptedJSON.split(","));
        }
        else if(!declinedJSON.equals("[]")) {
            declinedJSON = declinedJSON.substring(1, declinedJSON.length()-1);          
            rejectedList = Arrays.asList(declinedJSON.split(","));
        }  
        
        this.projectMatchService.saveAcceptedUsers(projectId, acceptedList);
        this.projectMatchService.saveRejectedUsers(projectId, rejectedList);
    }
}
