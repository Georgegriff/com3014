package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectApprovedDAO;
import com.com3014.group1.projectmatching.dao.ProjectDeclinedDAO;
import com.com3014.group1.projectmatching.dao.RoleDAO;
import com.com3014.group1.projectmatching.dao.UserMatchDAO;
import com.com3014.group1.projectmatching.dao.UserSetDAO;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.ProjectRole;
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
 * A service to provide User Matches to the REST service
 *
 * @author Daniel
 */
@Service
public class UserMatchService {

    @Autowired
    private UserMatchDAO userMatchDAO;

    @Autowired
    private UserSetDAO userSetDAO;

    @Autowired
    private ProjectApprovedDAO projectApprovedDAO;

    @Autowired
    private ProjectDeclinedDAO projectDeclinedDAO;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDAO roleDAO;

    /**
     * Save the matches for a User
     *
     * @param userEntity The User Entity to save the matches for
     * @param projectRoles The Roles that the User was matched to
     */
    @Transactional
    public void saveMatchesForUser(UserEntity userEntity, List<ProjectRole> projectRoles) {

        // Try to get the user match row from the db
        UserMatch userMatchEntity = this.userMatchDAO.findByUser(userEntity);
        // If there isn't one create a new one
        if (userMatchEntity == null) {
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
        for (int i = 0; i < projectRoles.size(); i++) {
            ProjectRole projectRole = projectRoles.get(i);

            UserSet userSetEntity = new UserSet();
            userSetEntity.setSet(userMatchEntity);
            userSetEntity.setProject(projectService.convertProjectToEntity(projectRole.getProject()));
            userSetEntity.setRole(projectRole.getRole());

            userSets.add(userSetEntity);
        }
        //Bulk insert
        this.userSetDAO.save(userSets);
    }

    /**
     * Retrieve the cached matches for a User
     *
     * @param userMatch The match object
     * @param userId The ID of the User
     * @return The list of Projects that were matched
     */
    public List<Project> retrieveCachedMatches(UserMatch userMatch, int userId) {
        List<UserSet> matches = this.userSetDAO.findBySet(userMatch);
        List<ProjectRole> projectRoles = new ArrayList<>();
        List<Project> projects = new ArrayList<>();

        for (UserSet match : matches) {
            Project projectMatch = this.projectService.convertEntityToProjectAllData(match.getProject());
            RoleEntity roleMatch = match.getRole();
            projectRoles.add(new ProjectRole(projectMatch, roleMatch));
        }

        // Get the accepted and rejected users for this project
        List<ProjectRole> swiped = getAlreadySwipedProjects(userId);
        // Remove all users from the users list that are also in the swiped list
        projectRoles.removeAll(swiped);

        //Get the projects out that have not already been swiped
        for (ProjectRole projectRole : projectRoles) {
            projects.add(projectRole.getProject());
        }
        return projects;
    }

    /**
     * Get the list of Project Roles that have already been swiped by the User
     *
     * @param userId The ID of the User
     * @return The List of Project Roles that have already been swiped by the
     * User
     */
    public List<ProjectRole> getAlreadySwipedProjects(int userId) {
        List<ProjectApproved> approved = this.projectApprovedDAO.findByUser_UserId(userId);
        List<ProjectDeclined> declined = this.projectDeclinedDAO.findByUser_UserId(userId);

        List<ProjectRole> combined = new ArrayList<>();

        for (ProjectApproved project : approved) {
            Project projectMatch = this.projectService.convertEntityToProjectAllData(project.getProject());
            RoleEntity roleMatch = project.getRole();
            combined.add(new ProjectRole(projectMatch, roleMatch));
        }
        for (ProjectDeclined project : declined) {
            Project projectMatch = this.projectService.convertEntityToProjectAllData(project.getProject());
            RoleEntity roleMatch = project.getRole();
            combined.add(new ProjectRole(projectMatch, roleMatch));
        }
        return combined;
    }

    /**
     * Fine a match for the given User
     *
     * @param entity The User Entity to find a match for
     * @return The match
     */
    public UserMatch findMatchForUser(UserEntity entity) {
        return this.userMatchDAO.findByUser(entity);
    }

    /**
     * Get Users that have been matched to the given Project
     *
     * @param project The Project to get User matches for
     * @return The list of approved Users
     */
    public List<ProjectApproved> getUsersMatchedToProject(ProjectEntity project) {
        return projectApprovedDAO.findByProject(project);
    }

    /**
     * Set the cache expiry time
     *
     * @return The cache expiry time
     */
    private Date setCacheExpireTime() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // Add one day to the current date as the cache expire time
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return date;
    }

    /**
     * Save the list of Projects accepted by a user
     *
     * @param userId The ID of the User
     * @param acceptedProjects The accepted Projects
     */
    @Transactional
    public void saveAcceptedProjects(int userId, JSONArray acceptedProjects) {
        List<ProjectApproved> projectsApproved = new ArrayList<>();

        for (int i = 0; i < acceptedProjects.length(); i++) {
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

    /**
     * Save the list of Projects rejected by a user
     *
     * @param userId The ID of the User
     * @param rejectedProjects The rejected Projects
     */
    @Transactional
    public void saveRejectedProjects(int userId, JSONArray rejectedProjects) {
        List<ProjectDeclined> projectsRejected = new ArrayList<>();

        for (int i = 0; i < rejectedProjects.length(); i++) {
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
