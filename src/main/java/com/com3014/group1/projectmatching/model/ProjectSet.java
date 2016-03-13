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
@Table(name="project_sets")
@IdClass(ProjectSet.ProjectSetPK.class)
public class ProjectSet implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "set_id")
    ProjectMatch set;
    
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
  
    public ProjectSet() {
    }

    public void setSet(ProjectMatch set) {
        this.set = set;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProjectMatch getSet() {
        return set;
    }
    
    public static class ProjectSetPK implements Serializable {
        
        private Integer set;
        private Integer user;
        
        public ProjectSetPK() {
        }

        public Integer getSet() {
            return set;
        }

        public void setSet(Integer set) {
            this.set = set;
        }

        public Integer getUser() {
            return user;
        }

        public void setUser(Integer user) {
            this.user = user;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 73 * hash + Objects.hashCode(this.set);
            hash = 73 * hash + Objects.hashCode(this.user);
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
            final ProjectSetPK other = (ProjectSetPK) obj;
            if (!Objects.equals(this.set, other.set)) {
                return false;
            }
            if (!Objects.equals(this.user, other.user)) {
                return false;
            }
            return true;
        }
    }
    
}
