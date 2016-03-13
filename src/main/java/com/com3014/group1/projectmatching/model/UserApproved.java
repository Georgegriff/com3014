/*
 * To change this license header, choose License Headers in ProjectEntity Properties.
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
@Table(name = "users_approved")
@IdClass(UserApproved.UserApprovedPK.class)
public class UserApproved implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name ="user_id")
    private UserEntity user;
    
    @Id
    @ManyToOne
    @JoinColumn(name ="project_id")
    private ProjectEntity project;
    
    public UserApproved() {
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }   

    public UserEntity getUser() {
        return user;
    }
    
    public static class UserApprovedPK implements Serializable {
        
        private Integer user;
        private Integer project;
        
        public UserApprovedPK() {
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
            final UserApprovedPK other = (UserApprovedPK) obj;
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
