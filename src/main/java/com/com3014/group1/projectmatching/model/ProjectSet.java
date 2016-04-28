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
 * Database representation of a Set of Projects
 *
 * @author Daniel
 */
@Entity
@Table(name = "project_sets")
@IdClass(ProjectSet.ProjectSetPK.class)
public class ProjectSet implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "set_id")
    ProjectMatch set;

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @OneToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    public ProjectSet() {
    }

    public void setSet(ProjectMatch set) {
        this.set = set;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProjectMatch getSet() {
        return set;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public RoleEntity getRole() {
        return role;
    }

    public static class ProjectSetPK implements Serializable {

        private Integer set;
        private Integer user;
        private Integer role;

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

        public Integer getRole() {
            return role;
        }

        public void setRole(Integer role) {
            this.role = role;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 73 * hash + Objects.hashCode(this.set);
            hash = 73 * hash + Objects.hashCode(this.user);
            hash = 73 * hash + Objects.hashCode(this.role);
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
            if (!Objects.equals(this.role, other.role)) {
                return false;
            }
            return true;
        }
    }

}
