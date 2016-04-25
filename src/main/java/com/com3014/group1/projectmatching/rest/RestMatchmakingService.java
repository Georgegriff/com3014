/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.MatchmakingService;
import com.com3014.group1.projectmatching.core.services.ProjectService;
import com.com3014.group1.projectmatching.dto.MatchedUser;
import com.com3014.group1.projectmatching.matchmaking.Matches;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.UserProfile;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ggriffiths
 */
@RestController
@RequestMapping("/services/matches")
public class RestMatchmakingService {

    @Autowired
    private MatchmakingService matchmakingService;

    @Autowired
    private Matches matches;
    
    @Autowired
    private ProjectService projectService;
    
    
    @RequestMapping(value = "/project/{projectId}/role/{roleId}", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public List<UserProfile> matchRoleToUsers(@PathVariable String projectId, @PathVariable String roleId) {
        List<User> users = matchmakingService.matchUserToRole(Integer.parseInt(projectId), Integer.parseInt(roleId));
        List<UserProfile> publicUsers = new ArrayList<>();
        for(User user: users){
            publicUsers.add(new UserProfile(user));
        }
        return publicUsers;
    }

    @RequestMapping(value = "/user/roles", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public List<Project> matchUserToRoles(HttpSession session) {
        return matchmakingService.matchUserToProjectRoles(getCurrentUser(session));
    }
    
    @RequestMapping(value = "/project/{projectId}/save", method = RequestMethod.POST)
    public void saveSwipedUsers(HttpSession session, @PathVariable String projectId, @RequestBody String preferences){
        if(!projectId.equals("null")) {        
            JSONObject preferencesJSON = new JSONObject(preferences);
            JSONArray preferencesArray = preferencesJSON.getJSONArray("preferences");

            JSONObject acceptedJSON = preferencesArray.getJSONObject(0);
            JSONObject rejectedJSON = preferencesArray.getJSONObject(1);

            JSONArray accepted = acceptedJSON.getJSONArray("accepted");
            JSONArray rejected = rejectedJSON.getJSONArray("rejected");
            
            this.matchmakingService.saveUserSwipePreferences(Integer.parseInt(projectId), accepted, rejected);
        }
    }
    
    @RequestMapping(value = "/user/save", method = RequestMethod.POST, headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public void saveSwipedProjects(HttpSession session, @RequestBody String preferences){
        JSONObject preferencesJSON = new JSONObject(preferences);
        JSONArray preferencesArray = preferencesJSON.getJSONArray("preferences");
        
        JSONObject acceptedJSON = preferencesArray.getJSONObject(0);
        JSONObject rejectedJSON = preferencesArray.getJSONObject(1);
        
        JSONArray accepted = acceptedJSON.getJSONArray("accepted");
        JSONArray rejected = rejectedJSON.getJSONArray("rejected");
        
        this.matchmakingService.saveProjectSwipePreferences(getCurrentUser(session).getUserId(), accepted, rejected);
    }
    
    @RequestMapping(value = "/project/{projectId}/matches", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public List<MatchedUser> findMutualMatchesForProject(HttpSession session, @PathVariable String projectId) {
        // Check if the current signed in user is the project owner
        if(checkUserPrivilege(session, Integer.parseInt(projectId))) {
            return matches.findMutualMatchesForProject(Integer.parseInt(projectId));
        }
        return null;
    }
    
    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("currentUser");
    }
    
    private boolean checkUserPrivilege(HttpSession session, int projectId) {
        ProjectEntity entity = projectService.findProjectById(projectId);
        User user = getCurrentUser(session);
        
        return entity.getProjectOwner().getUserId().equals(user.getUserId());
    }
}
