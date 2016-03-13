/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Daniel
 */
@Entity
@Table(name="role_qualifications")
@IdClass(RoleQualification.RoleQualificationPK.class)
public class RoleQualification implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name ="role_id")
    private Role role;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "qualification_level_id")
    private QualificationLevel qualificationLevel;
    
    @Column (name = "subject")
    private String subject;
    
    @Column (name = "required")
    private boolean required;
    
    public RoleQualification() {
    }

    public void setRole(Role role) {
        this.role = role;
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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Role getRole() {
        return role;
    }
    
    public static class RoleQualificationPK implements Serializable {
        
        private Integer role;
        private Integer qualificationLevel;
        
        public RoleQualificationPK() {
        }

        public Integer getRole() {
            return role;
        }

        public void setRole(Integer role) {
            this.role = role;
        }

        public Integer getQualificationLevel() {
            return qualificationLevel;
        }

        public void setQualificationLevel(Integer qualificationLevel) {
            this.qualificationLevel = qualificationLevel;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + Objects.hashCode(this.role);
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
            final RoleQualificationPK other = (RoleQualificationPK) obj;
            if (!Objects.equals(this.role, other.role)) {
                return false;
            }
            if (!Objects.equals(this.qualificationLevel, other.qualificationLevel)) {
                return false;
            }
            return true;
        }
        
        
    }
    
}
