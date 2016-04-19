/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dto;

/**
 *
 * @author George
 */
public class MatchedUser {

    private UserProfile user;
    private Project project;
    private Role role;

    public MatchedUser(User user, Project project, Role role) {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
