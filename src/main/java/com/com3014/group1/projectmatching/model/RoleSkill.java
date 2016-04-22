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
@Table(name = "role_skills")
@IdClass(RoleSkill.RoleSkillPK.class)
public class RoleSkill implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name ="project_role_id")
    private ProjectRoleEntity projectRole;
    
    @Id
    @ManyToOne
    @JoinColumn(name ="skill_id")
    private Skill skill;
    
    @Column (name = "required")
    private boolean required;
    
    public RoleSkill() {
    }
    
    public void setProjectRoleId(ProjectRoleEntity projectRole) {
        this.projectRole = projectRole;
    }
    
    public ProjectRoleEntity getProjectRoleId() {
        return this.projectRole;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
    
    public static class RoleSkillPK implements Serializable {
        
        private Integer projectRole;
        private Integer skill;
        
        public RoleSkillPK() {
        }

        public Integer getProjectRole() {
            return projectRole;
        }

        public void setProjectRole(Integer projectRole) {
            this.projectRole = projectRole;
        }

        public Integer getSkill() {
            return skill;
        }

        public void setSkill(Integer skill) {
            this.skill = skill;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + Objects.hashCode(this.projectRole);
            hash = 17 * hash + Objects.hashCode(this.skill);
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
            final RoleSkillPK other = (RoleSkillPK) obj;
            if (!Objects.equals(this.projectRole, other.projectRole)) {
                return false;
            }
            if (!Objects.equals(this.skill, other.skill)) {
                return false;
            }
            return true;
        }
        
        
    }
}
