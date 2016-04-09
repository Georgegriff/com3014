/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.MatchmakingService;
import com.com3014.group1.projectmatching.matchmaking.Matches;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.User;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @RequestMapping(value = "/project/{projectId}/role/{roleId}", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public List<User> matchRoleToUsers(@PathVariable String projectId, @PathVariable String roleId) {
        return matchmakingService.matchUserToRole(Integer.parseInt(projectId), Integer.parseInt(roleId));
    }

    @RequestMapping(value = "/user/roles", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public List<Project> matchUserToRoles(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        return matchmakingService.matchUserToProjectRoles(currentUser);
    }
    
    // TODO, change this to a PUT request. Need to see how that is implemented from the js stuff
    @RequestMapping(value = "/project/{projectId}/save/{accepted}/{rejected}", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public void saveSwipedUsers(@PathVariable String projectId, @PathVariable String accepted, @PathVariable String rejected) {
        if(!projectId.equals("null")) {
            this.matchmakingService.saveUserSwipePreferences(Integer.parseInt(projectId), accepted, rejected);
            
            // return matches.searchForMatchUser(Integer.parseInt(projectId));
        }
    }
    
    // TODO, change this to a PUT request. Need to see how that is implemented from the js stuff
    @RequestMapping(value = "/user/{userId}/save/{accepted}/{rejected}", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public void saveSwipedProjects(@PathVariable String userId, @PathVariable String accepted, @PathVariable String rejected) {
        if(!userId.equals("null")) {
            this.matchmakingService.saveProjectSwipePreferences(Integer.parseInt(userId), accepted, rejected);
        }
    }
}
