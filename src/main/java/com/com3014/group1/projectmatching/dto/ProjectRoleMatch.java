/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dto;

import java.util.Objects;


/**
 *
 * @author Daniel
 */
public class ProjectRoleMatch {
    
    private Project project;
    private Role role;
   
    public ProjectRoleMatch() {
        super();
    }
    
    public ProjectRoleMatch(Project project, Role role) {
        super();
        this.project = project;
        this.role = role;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.project);
        hash = 29 * hash + Objects.hashCode(this.role);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProjectRoleMatch other = (ProjectRoleMatch) obj;
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        return true;
    }
    
    
}
