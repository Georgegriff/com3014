/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.ProjectRoleMatch;
import com.com3014.group1.projectmatching.dto.Role;
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
            users = matching.findUsersForRole(role, projectId);
            this.projectMatchService.saveMatchesForProject(projectEntity, users, role);
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
            List<ProjectRoleMatch> projectRoles = matching.findRolesForUser(user);
            System.out.println("Size of Projects: " + projectRoles.get(1).getProject().getProjectId() + " " + projectRoles.get(0).getProject().getProjectId());
            for(ProjectRoleMatch projectRole : projectRoles) {
                projects.add(projectRole.getProject());
            }
            
            this.userMatchService.saveMatchesForUser(userEntity, projectRoles);
        }
        // Else, retrieve the cached set
        else {
            projects = this.userMatchService.retrieveCachedMatches(userMatch, user.getUserId());
        }
        return projects;
    }    
    
    public void saveProjectSwipePreferences(Integer userId, String accepted, String declined) {
        try {
            JSONObject acceptedJson = new JSONObject(accepted);
            JSONObject delcinedJson = new JSONObject(declined);

            JSONArray acceptedArray = acceptedJson.getJSONArray("accepted");
            this.userMatchService.saveAcceptedProjects(userId, acceptedArray);

            JSONArray declinedArray = delcinedJson.getJSONArray("rejected");
            this.userMatchService.saveRejectedProjects(userId, declinedArray);
        }
        catch(Exception e) {
            //Need to decide how we want to relay back to user
            e.printStackTrace();
        }
    }
    
    public void saveUserSwipePreferences(Integer projectId, String accepted, String declined) {
        try {
            JSONObject acceptedJSON = new JSONObject(accepted);
            JSONObject declinedJSON = new JSONObject(declined);

            JSONArray acceptedArray = acceptedJSON.getJSONArray("accepted");
            this.projectMatchService.saveAcceptedUsers(projectId, acceptedArray);
               
            JSONArray declinedArray = declinedJSON.getJSONArray("rejected");
            this.projectMatchService.saveRejectedUsers(projectId, declinedArray);
        }
        catch (Exception e) {
            //TODO need to decide how we are propogating errors
            e.printStackTrace();
        }
    }
}
