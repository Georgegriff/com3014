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
 * REST Service for matchmaking
 *
 * @author George
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

    /**
     * Get a list of @User that match the @Role that is within the @Project
     *
     * @param roleId The ID of the @Role to match with
     * @param projectId The ID of the @Project that the @Role is in
     * @return The list of @User that matched
     */
    @RequestMapping(value = "/project/{projectId}/role/{roleId}", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public List<UserProfile> matchRoleToUsers(@PathVariable String projectId, @PathVariable String roleId) {
        List<User> users = matchmakingService.matchUserToRole(Integer.parseInt(projectId), Integer.parseInt(roleId));
        List<UserProfile> publicUsers = new ArrayList<>();
        for (User user : users) {
            publicUsers.add(new UserProfile(user));
        }
        return publicUsers;
    }

    /**
     * Get a list of @Project that match the @User
     *
     * @param session The session used to obtain the current user
     * @return The list of @Project that matched
     */
    @RequestMapping(value = "/user/roles", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public List<Project> matchUserToRoles(HttpSession session) {
        return matchmakingService.matchUserToProjectRoles(getCurrentUser(session));
    }

    /**
     * Save the accept or decline of a @User by a @Project manager
     *
     * @param projectId The ID of the @Project
     * @param session The session used to obtain the current user
     * @param preferences String representation of the acceptance and rejection
     * of Users
     */
    @RequestMapping(value = "/project/{projectId}/save", method = RequestMethod.POST)
    public void saveSwipedUsers(HttpSession session, @PathVariable String projectId, @RequestBody String preferences) {
        if (!projectId.equals("null")) {
            JSONObject preferencesJSON = new JSONObject(preferences);
            JSONArray preferencesArray = preferencesJSON.getJSONArray("preferences");

            JSONObject acceptedJSON = preferencesArray.getJSONObject(0);
            JSONObject rejectedJSON = preferencesArray.getJSONObject(1);

            JSONArray accepted = acceptedJSON.getJSONArray("accepted");
            JSONArray rejected = rejectedJSON.getJSONArray("rejected");

            this.matchmakingService.saveUserSwipePreferences(Integer.parseInt(projectId), accepted, rejected);
        }
    }

    /**
     * Save the accept or decline of a @Project @Role by a @User
     *
     * @param session The session used to obtain the current user
     * @param preferences String representation of the acceptance and rejection
     * of Roles
     */
    @RequestMapping(value = "/user/save", method = RequestMethod.POST, headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public void saveSwipedProjects(HttpSession session, @RequestBody String preferences) {
        JSONObject preferencesJSON = new JSONObject(preferences);
        JSONArray preferencesArray = preferencesJSON.getJSONArray("preferences");

        JSONObject acceptedJSON = preferencesArray.getJSONObject(0);
        JSONObject rejectedJSON = preferencesArray.getJSONObject(1);

        JSONArray accepted = acceptedJSON.getJSONArray("accepted");
        JSONArray rejected = rejectedJSON.getJSONArray("rejected");

        this.matchmakingService.saveProjectSwipePreferences(getCurrentUser(session).getUserId(), accepted, rejected);
    }

    /**
     * Find the mutual matches for a Project, the Users that have approved the
     * Project that the Project manager has also approved
     *
     * @param session The session used to obtain the current user
     * @param projectId The ID of the Project
     * @return The mutual matches
     */
    @RequestMapping(value = "/project/{projectId}/matches", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public List<MatchedUser> findMutualMatchesForProject(HttpSession session, @PathVariable String projectId) {
        // Check if the current signed in user is the project owner
        if (checkUserPrivilege(session, Integer.parseInt(projectId))) {
            return matches.findMutualMatchesForProject(Integer.parseInt(projectId));
        }
        return null;
    }

    /**
     * Get the current user from the session
     *
     * @param session The session
     * @return The current user
     */
    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("currentUser");
    }

    /**
     * Check the user privilege
     *
     * @param session The Session
     * @param projectId The ID of the Project
     * @return Whether the User owns the project
     */
    private boolean checkUserPrivilege(HttpSession session, int projectId) {
        ProjectEntity entity = projectService.findProjectById(projectId);
        User user = getCurrentUser(session);

        return entity.getProjectOwner().getUserId().equals(user.getUserId());
    }
}
