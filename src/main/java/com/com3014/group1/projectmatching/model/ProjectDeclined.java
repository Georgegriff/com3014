/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "projects_declined")
@IdClass(ProjectDeclined.ProjectDeclinedPK.class)
public class ProjectDeclined implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name ="project_id")
    private Project project;
    
    @Id
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;
    
    public ProjectDeclined() {
    }

    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public void setProject(Project project) {
        this.project = project;
    }   

    public Project getProject() {
        return project;
    }
    
    public static class ProjectDeclinedPK implements Serializable {
        
        private Integer project;
        private Integer user;
        
        public ProjectDeclinedPK() {
        }

        public Integer getUser() {
            return user;
        }

        public void setUser(Integer user) {
            this.user = user;
        }

        public Integer getProject() {
            return project;
        }

        public void setProject(Integer project) {
            this.project = project;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 83 * hash + Objects.hashCode(this.user);
            hash = 83 * hash + Objects.hashCode(this.project);
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
            final ProjectDeclinedPK other = (ProjectDeclinedPK) obj;
            if (!Objects.equals(this.user, other.user)) {
                return false;
            }
            if (!Objects.equals(this.project, other.project)) {
                return false;
            }
            return true;
        } 
    }
}