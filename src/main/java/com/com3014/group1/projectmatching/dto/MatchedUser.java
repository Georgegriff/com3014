/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dto;

import com.com3014.group1.projectmatching.model.RoleEntity;

/**
 *
 * @author George
 */
public class MatchedUser {

    private UserProfile user;
    private Project project;
    private RoleEntity role;

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
