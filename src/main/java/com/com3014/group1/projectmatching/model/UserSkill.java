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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name="user_skills")
@IdClass(UserSkill.UserSkillPK.class)
public class UserSkill implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name ="user_id")
    private UserEntity user;
    
    @Id
    @ManyToOne
    @JoinColumn(name ="skill_id")
    private Skill skill;
    
    @NotNull
    @Column(name = "months_of_experience")
    private int monthsOfExperience;
    
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    public Skill getSkill() {
        return skill;
    }
    
    public void setSkill(Skill skill) {
        this.skill = skill;
    }
   
    public Integer getMonthsOfExperience() {
        return monthsOfExperience;
    }

    public void setMonthsOfExperience(Integer monthsOfExperience) {
        this.monthsOfExperience = monthsOfExperience;
    }
    
    public static class UserSkillPK implements Serializable {
        
        private Integer user;
        private Integer skill;

        public UserSkillPK() {
        }

        public Integer getUser() {
            return user;
        }

        public void setUser(Integer user) {
            this.user = user;
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
            hash = 79 * hash + Objects.hashCode(this.user);
            hash = 79 * hash + Objects.hashCode(this.skill);
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
            final UserSkillPK other = (UserSkillPK) obj;
            return true;
        }
    }
}
