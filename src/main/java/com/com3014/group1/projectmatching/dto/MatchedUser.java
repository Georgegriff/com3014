package com.com3014.group1.projectmatching.dto;

import com.com3014.group1.projectmatching.model.RoleEntity;

/**
 * The definition of a @UserProfile that has been matched with a @RoleEntity
 * within a @Project
 *
 * @author George
 */
public class MatchedUser {

    /**
     * The User Profile of a @User that has been matched
     */
    private UserProfile user;
    /**
     * The Project that the @RoleEntity of the match belongs to
     */
    private Project project;
    /**
     * The RoleEntity that the @User has matched to
     */
    private RoleEntity role;

    /**
     * Constructor
     *
     * @param user The User Profile of a @User that has been matched
     * @param project The Project that the @RoleEntity of the match belongs to
     * @param role The RoleEntity that the @User has matched to
     */
    public MatchedUser(User user, Project project, RoleEntity role) {
        this.user = new UserProfile(user);
        this.user.provideEmail(user.getEmail());
        this.project = project;
        this.role = role;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

}
