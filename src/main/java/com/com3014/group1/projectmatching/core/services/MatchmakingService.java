/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.ProjectRole;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.matchmaking.Matchmaking;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectMatch;
import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserMatch;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
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
        ProjectRole projectRole = roleService.findByProjectRole(projectId, roleId);
        ProjectEntity projectEntity = this.projectService.findProjectById(projectId);
        ProjectMatch projectMatch = this.projectMatchService.findMatchForProject(projectEntity);
        List<User> users = null;

        
        if(projectRole == null) {
            return new ArrayList<>();
        }
        // If we dont have a current match entry and we have a valid role
        // or if the match set cache has expired then rerun the algorithm
        else if((projectMatch == null) || (projectMatch.getCacheExpire().before(new Date()))) {
            users = matching.findUsersForRole(projectRole);
            this.projectMatchService.saveMatchesForProject(projectEntity, users, projectRole.getRole());
        } 
        // Else, retrieve cached set
        else {
            users = this.projectMatchService.retrieveCachedMatches(projectMatch, projectId);
        }
        
        return users;
    }

    public List<Project> matchUserToProjectRoles(User user) {
        UserEntity userEntity = userService.convertUserToEntity(user);
        UserMatch userMatch = userMatchService.findMatchForUser(userEntity);
        List<Project> projects = new ArrayList<>();
        // If there is no match in the database or the cache has expired, 
        // rerun the algorithm and save the new set
        if((userMatch == null) || (userMatch.getCacheExpire().before(new Date()))) {
            List<ProjectRole> projectRoles = matching.findRolesForUser(user);
            
            for(ProjectRole projectRole : projectRoles) {
                Project project = projectRole.getProject();
                this.projectService.getRemainingData(projectRole.getProject());
                projects.add(project);
            }
            
            this.userMatchService.saveMatchesForUser(userEntity, projectRoles);
        }
        // Else, retrieve the cached set
        else {
            projects = this.userMatchService.retrieveCachedMatches(userMatch, user.getUserId());
        }
        return projects;
    }    
    
    public void saveProjectSwipePreferences(Integer userId, JSONArray accepted, JSONArray declined) {
        this.userMatchService.saveAcceptedProjects(userId, accepted);
        this.userMatchService.saveRejectedProjects(userId, declined);
    }
    
    public void saveUserSwipePreferences(Integer projectId, JSONArray accepted, JSONArray declined) {
        this.projectMatchService.saveAcceptedUsers(projectId, accepted);       
        this.projectMatchService.saveRejectedUsers(projectId, declined);
    }
}
