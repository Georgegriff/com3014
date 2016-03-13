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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name="user_sets")
@IdClass(UserSet.UserSetPK.class)
public class UserSet implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "set_id")
    UserMatch set;
    
    @Id
    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;
    
    public UserSet() {
    }
    
    public void setSet(UserMatch set) {
        this.set = set;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    } 

    public UserMatch getSet() {
        return set;
    }
    
    public static class UserSetPK implements Serializable {
        
        private Integer set;
        private Integer project;
        
        public UserSetPK() {
        }

        public Integer getSet() {
            return set;
        }

        public void setSet(Integer set) {
            this.set = set;
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
            hash = 41 * hash + Objects.hashCode(this.set);
            hash = 41 * hash + Objects.hashCode(this.project);
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
            final UserSetPK other = (UserSetPK) obj;
            if (!Objects.equals(this.set, other.set)) {
                return false;
            }
            if (!Objects.equals(this.project, other.project)) {
                return false;
            }
            return true;
        }        
    }
}
