/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.matchmaking;

import com.com3014.group1.projectmatching.core.services.UserMatchService;
import com.com3014.group1.projectmatching.core.services.ProjectMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.UserEntity;
import java.util.List;

/**
 *
 * @author Elliot
 */
public class Matches {
    
    @Autowired
    private UserMatchService userMatchService;
    
    @Autowired
    private ProjectMatchService projectMatchService;
    
    // we have swiped accept for a user and we want to see if that user has already swiped for the role
    private int searchForMatchUser(UserEntity user, ProjectEntity project) {
    
        List<UserEntity> usersMatched = userMatchService.getUsersMatchedToProject(project);
        int i = 0;
        
        if (usersMatched.contains(user)) {
            // We have a match! send some emails?
            i = 1;
        }
        return i;
    }
    
    // we have swiped accept for a project and we want to see if the project owner has already swiped for this user
    private int searchForMatchProject(UserEntity user, ProjectEntity project) {
    
        List<ProjectEntity> projectsMatched = projectMatchService.getProjectsMatchedToUser(user);
        int i = 0;
        
        if (projectsMatched.contains(project)) {
            // We have a match! send some emails?
            i = 1;
        }
        return i;
    }
}
