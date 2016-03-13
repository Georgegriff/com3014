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
@Table(name="project_role")
@IdClass(ProjectRole.ProjectRolePK.class)
public class ProjectRole implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name ="project_id")
    private ProjectEntity project;
    
    @Id
    @ManyToOne
    @JoinColumn(name ="role_id")
    private RoleEntity role;
    
    public ProjectRole() {
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
    
    public static class ProjectRolePK implements Serializable {
        
        private Integer project;
        private Integer role;
        
        public ProjectRolePK() {
        }

        public Integer getProject() {
            return project;
        }

        public void setProject(Integer project) {
            this.project = project;
        }

        public Integer getRole() {
            return role;
        }

        public void setRole(Integer role) {
            this.role = role;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 41 * hash + Objects.hashCode(this.project);
            hash = 41 * hash + Objects.hashCode(this.role);
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
            final ProjectRolePK other = (ProjectRolePK) obj;
            if (!Objects.equals(this.project, other.project)) {
                return false;
            }
            if (!Objects.equals(this.role, other.role)) {
                return false;
            }
            return true;
        } 
    }
    
}
