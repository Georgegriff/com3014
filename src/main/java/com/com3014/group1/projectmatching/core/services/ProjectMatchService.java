package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectMatchDAO;
import com.com3014.group1.projectmatching.dao.ProjectSetDAO;
import com.com3014.group1.projectmatching.dao.RoleDAO;
import com.com3014.group1.projectmatching.dao.UserApprovedDAO;
import com.com3014.group1.projectmatching.dao.UserDeclinedDAO;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectMatch;
import com.com3014.group1.projectmatching.model.ProjectSet;
import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.UserApproved;
import com.com3014.group1.projectmatching.model.UserDeclined;
import com.com3014.group1.projectmatching.model.UserEntity;
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
 * A service to provide Project matches to the REST Service
 *
 * @author Daniel
 */
@Service
public class ProjectMatchService {

    @Autowired
    private ProjectMatchDAO projectMatchDAO;

    @Autowired
    private ProjectSetDAO projectSetDAO;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserApprovedDAO userApprovedDAO;

    @Autowired
    private UserDeclinedDAO userDeclinedDAO;

    @Autowired
    private RoleDAO roleDAO;

    /**
     * Find a match for the project
     *
     * @param projectEntity The project to find a match for
     * @return The project match
     */
    public ProjectMatch findMatchForProject(ProjectEntity projectEntity) {
        return this.projectMatchDAO.findByProject(projectEntity);
    }

    /**
     * Save the matches for a project
     *
     * @param projectEntity The project to save the matches for
     * @param users The users that have been matched with the role within the
     * project
     * @param roleEntity The role within the project that has been matched to
     */
    @Transactional
    public void saveMatchesForProject(ProjectEntity projectEntity, List<User> users, RoleEntity roleEntity) {

        ProjectMatch projectMatchEntity = this.projectMatchDAO.findByProject(projectEntity);
        List<ProjectSet> projectSets = new ArrayList<>();

        if (projectMatchEntity == null) {
            projectMatchEntity = new ProjectMatch();
        }

        projectMatchEntity.setProject(projectEntity);
        projectMatchEntity.setCacheExpire(setCacheExpireTime());
        projectMatchEntity.setStatusControl("C");

        this.projectMatchDAO.save(projectMatchEntity);
        this.projectSetDAO.deleteBySet(projectMatchEntity);

        for (int i = 0; i < users.size(); i++) {
            ProjectSet projectSetEntity = new ProjectSet();
            projectSetEntity.setSet(projectMatchEntity);
            projectSetEntity.setUser(this.userService.convertUserToEntity(users.get(i)));
            projectSetEntity.setRole(roleEntity);

            projectSets.add(projectSetEntity);
        }
        // Bulk insert
        this.projectSetDAO.save(projectSets);

    }

    /**
     * Retrieve the cached matches for a project
     *
     * @param projectMatch The project match object
     * @param projectId The ID of the project
     * @return The list of matched users
     */
    public List<User> retrieveCachedMatches(ProjectMatch projectMatch, int projectId) {
        List<ProjectSet> matches = this.projectSetDAO.findBySet(projectMatch);
        List<UserEntity> users = new ArrayList<>();

        for (ProjectSet match : matches) {
            users.add(match.getUser());
        }
        // Get the accepted and rejected users for this project
        List<UserEntity> swiped = getAlreadySwipedUsers(projectId);
        // Remove all users from the users list that are also in the swiped list
        users.removeAll(swiped);

        return this.userService.convertEntityListToUserList(users);
    }

    /**
     * Get the list of users that have already been swiped by the project
     * manager
     *
     * @param projectId The ID of the project
     * @return
     */
    protected List<UserEntity> getAlreadySwipedUsers(int projectId) {
        List<UserApproved> approved = this.userApprovedDAO.findByProject_ProjectId(projectId);
        List<UserDeclined> declined = this.userDeclinedDAO.findByProject_ProjectId(projectId);
        List<UserEntity> combined = new ArrayList<>();

        for (UserApproved user : approved) {
            combined.add(user.getUser());
        }
        for (UserDeclined user : declined) {
            combined.add(user.getUser());
        }

        return combined;
    }

    /**
     * Get the list of Project Roles approved by the user
     *
     * @param user The user
     * @return The list of project roles approved by the user
     */
    public List<UserApproved> getProjectsMatchedToUser(UserEntity user) {
        return userApprovedDAO.findByUser(user);
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
     * Save the list of users accepted by the project manager
     *
     * @param projectId The ID of the project
     * @param acceptedUsers The accepted users
     */
    @Transactional
    public void saveAcceptedUsers(int projectId, JSONArray acceptedUsers) {
        List<UserApproved> usersApproved = new ArrayList<>();

        for (int i = 0; i < acceptedUsers.length(); i++) {
            JSONObject projectRole = acceptedUsers.getJSONObject(i);

            ProjectEntity projectEntity = this.projectService.findProjectById(projectId);
            UserEntity userEntity = this.userService.findEntityById(projectRole.getInt("user"));
            RoleEntity roleEntity = this.roleDAO.findOne(projectRole.getInt("role"));

            UserApproved approvedEntity = new UserApproved();
            approvedEntity.setUser(userEntity);
            approvedEntity.setProject(projectEntity);
            approvedEntity.setRole(roleEntity);

            usersApproved.add(approvedEntity);
        }
        this.userApprovedDAO.save(usersApproved);
    }

    /**
     * Save the list of users rejected by the project manager
     *
     * @param projectId The ID of the project
     * @param rejectedUsers The rejected users
     */
    @Transactional
    public void saveRejectedUsers(int projectId, JSONArray rejectedUsers) {
        List<UserDeclined> usersDeclined = new ArrayList<>();

        for (int i = 0; i < rejectedUsers.length(); i++) {
            JSONObject projectRole = rejectedUsers.getJSONObject(i);

            ProjectEntity projectEntity = this.projectService.findProjectById(projectId);
            UserEntity userEntity = this.userService.findEntityById(projectRole.getInt("user"));
            RoleEntity roleEntity = this.roleDAO.findOne(projectRole.getInt("role"));

            UserDeclined declinedEntity = new UserDeclined();
            declinedEntity.setUser(userEntity);
            declinedEntity.setProject(projectEntity);
            declinedEntity.setRole(roleEntity);

            usersDeclined.add(declinedEntity);
        }
        this.userDeclinedDAO.save(usersDeclined);
    }
}
