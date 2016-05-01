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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A service to provide Matchmaking methods to the REST Service from the
 * Matchmaking algorithm
 *
 * @author George
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
     * Get a list of @User that match the @Role that is within the @Project
     *
     * @param roleId The ID of the @Role to match with
     * @param projectId The ID of the @Project that the @Role is in
     * @return The list of @User that matched
     */
    public List<User> matchUserToRole(int projectId, int roleId) {
        ProjectRole projectRole = roleService.findByProjectRole(projectId, roleId);
        ProjectEntity projectEntity = this.projectService.findProjectById(projectId);
        ProjectMatch projectMatch = this.projectMatchService.findMatchForProject(projectEntity);
        List<User> users = new ArrayList<>();

        // If there isn't a project role then return the empty list
        if (projectRole == null) {
            return users;
        } 
        // If we dont have a current match entry and we have a valid role
        // or if the match set cache has expired then rerun the algorithm
        else if ((projectMatch == null) || (projectMatch.getCacheExpire().before(new Date()))) {
            users = matching.findUsersForRole(projectRole);
            //Remove the project owner from the list of match users incase they so happen to match
            users.remove(this.userService.convertEntityToUser(projectEntity.getProjectOwner()));
            this.projectMatchService.saveMatchesForProject(projectEntity, users, projectRole.getRole());
        } // Else, retrieve cached set
        else {
            users = this.projectMatchService.retrieveCachedMatches(projectMatch, projectId);
        }

        return users;
    }

    /**
     * Get a list of @Project that match the @User
     *
     * @param user The @User to find the @Project matched for
     * @return The list of @Project that matched
     */
    public List<Project> matchUserToProjectRoles(User user) {
        UserEntity userEntity = userService.convertUserToEntity(user);
        UserMatch userMatch = userMatchService.findMatchForUser(userEntity);
        List<Project> projects = new ArrayList<>();
        // If there is no match in the database or the cache has expired, 
        // rerun the algorithm and save the new set
        if ((userMatch == null) || (userMatch.getCacheExpire().before(new Date()))) {
            List<ProjectRole> projectRoles = matching.findRolesForUser(user);

            for (ProjectRole projectRole : projectRoles) {
                // Get the project associated with each role
                Project project = projectRole.getProject();
                // Grab the skills, qualifications and interest associated with each project
                this.projectService.getRemainingData(projectRole.getProject());
                
                // Add project to list as long as the project is not owned by the user
                if(project.getProjectOwner() != user.getUserId()) {
                    projects.add(project);
                }
            }

            this.userMatchService.saveMatchesForUser(userEntity, projectRoles);
        } // Else, retrieve the cached set
        else {
            projects = this.userMatchService.retrieveCachedMatches(userMatch, user.getUserId());
        }
        return projects;
    }

    /**
     * Save the accept or decline of a @Project @Role by a @User
     *
     * @param userId The ID of the @User
     * @param accepted The JSON Array of accepted @Project
     * @param declined The JSON array of declined @Project
     */
    public void saveProjectSwipePreferences(Integer userId, JSONArray accepted, JSONArray declined) {
        this.userMatchService.saveAcceptedProjects(userId, accepted);
        this.userMatchService.saveRejectedProjects(userId, declined);
    }

    /**
     * Save the accept or decline of a @User by a @Project manager
     *
     * @param projectId The ID of the @Project
     * @param accepted The JSON Array of accepted @User
     * @param declined The JSON Array of accepted @User
     */
    public void saveUserSwipePreferences(Integer projectId, JSONArray accepted, JSONArray declined) {
        this.projectMatchService.saveAcceptedUsers(projectId, accepted);
        this.projectMatchService.saveRejectedUsers(projectId, declined);
    }
}
