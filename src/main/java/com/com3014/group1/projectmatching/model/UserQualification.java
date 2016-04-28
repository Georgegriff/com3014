package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Database representation of a @User Qualification
 *
 * @author Sam Waters
 * @author Dan Ashworth
 */
@Entity
@Table(name = "user_qualifications")
@IdClass(UserQualification.UserQualificationPK.class)
public class UserQualification implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "qualification_level_id")
    private QualificationLevel qualificationLevel;

    @NotNull
    @Column(name = "subject")
    private String subject;

    public UserQualification() {
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public QualificationLevel getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(QualificationLevel qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public static class UserQualificationPK implements Serializable {

        private Integer user;
        private Integer qualificationLevel;

        public UserQualificationPK() {
        }

        public Integer getUser() {
            return user;
        }

        public void setUser(Integer user) {
            this.user = user;
        }

        public Integer getQualificationLevel() {
            return qualificationLevel;
        }

        public void setQualificationLevel(Integer qualificationLevel) {
            this.qualificationLevel = qualificationLevel;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 23 * hash + Objects.hashCode(this.user);
            hash = 23 * hash + Objects.hashCode(this.qualificationLevel);
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
            final UserQualificationPK other = (UserQualificationPK) obj;
            if (!Objects.equals(this.user, other.user)) {
                return false;
            }
            if (!Objects.equals(this.qualificationLevel, other.qualificationLevel)) {
                return false;
            }
            return true;
        }

    }

}
