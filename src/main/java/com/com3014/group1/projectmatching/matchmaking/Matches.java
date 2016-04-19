/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.matchmaking;

import com.com3014.group1.projectmatching.core.services.UserMatchService;
import com.com3014.group1.projectmatching.core.services.ProjectMatchService;
import com.com3014.group1.projectmatching.core.services.ProjectRoleService;
import com.com3014.group1.projectmatching.core.services.ProjectService;
import com.com3014.group1.projectmatching.core.services.UserService;
import com.com3014.group1.projectmatching.dto.MatchedUser;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.Role;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.model.ProjectApproved;
import org.springframework.beans.factory.annotation.Autowired;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.UserApproved;
import com.com3014.group1.projectmatching.model.UserEntity;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elliot & George
 */
@Service
public class Matches {

    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private ProjectMatchService projectMatchService;

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ProjectRoleService projectRoleService;
    
    @Autowired
    private UserService userService;

    public List<MatchedUser> findMutualMatchesForProject(int projectId) {
        ProjectEntity project = projectService.findProjectById(projectId);
        List<MatchedUser> mutualMatchedUsers = new ArrayList<MatchedUser>();
        List<ProjectApproved> usersMatches = userMatchService.getUsersMatchedToProject(project);
        for (ProjectApproved userMatched : usersMatches) {
            UserEntity user = userMatched.getUser();
            List<UserApproved> userProjectsMatched = projectMatchService.getProjectsMatchedToUser(user);
            for(UserApproved approvedProject : userProjectsMatched) {
                if ((approvedProject.getProject().equals(project))) {
                    try {
                        
                        mutualMatchedUsers.add(convertToMatchedUser(userMatched));
                    }catch(ObjectNotFoundException ex){

                    }
                }
            }
        }
        return mutualMatchedUsers;
    }

    /**
     * Check if a user is contained within a list of users.
     *
     * @param user
     * @param matches
     * @return
     */
    private boolean searchForMatchedUser(UserApproved user, List<UserApproved> matches) {
        return matches.contains(user);
    }

    /**
     * Check if a project is contained within a list of projects
     *
     * @param project
     * @param matches
     * @return
     */
    private boolean searchForMatchedProject(ProjectApproved project, List<ProjectApproved> matches) {
        return matches.contains(project);
    }

    private MatchedUser convertToMatchedUser(ProjectApproved userMatched) {
        User user = userService.convertEntityToUser(userMatched.getUser());
        Project project = projectService.convertEntityToProject(userMatched.getProject());
        Role role = projectRoleService.convertEntitiesToRoles(userMatched.getRole());
        return new MatchedUser(user, project, role);
    }

}
