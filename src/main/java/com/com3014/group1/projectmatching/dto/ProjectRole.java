/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dto;

import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.RoleQualification;
import com.com3014.group1.projectmatching.model.RoleSkill;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Daniel
 */
public class ProjectRole {
    
    private Project project;
    
    private RoleEntity role;
    
    private List<RoleSkill> skillsList;
    
    private List<RoleQualification> qualificationsList;
    
    public ProjectRole(Project project, RoleEntity role, List<RoleSkill> roleSkills, List<RoleQualification> roleQualifications) {
        super();
        this.project = project;
        this.role = role;
        this.skillsList = roleSkills;
        this.qualificationsList = roleQualifications;
    }
    
    public ProjectRole(Project project, RoleEntity role) {
        super();
        this.project = project;
        this.role = role;
    }
   
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public List<RoleSkill> getSkillsList() {
        return skillsList;
    }

    public void setSkillsList(List<RoleSkill> skillsList) {
        this.skillsList = skillsList;
    }

    public List<RoleQualification> getQualificationsList() {
        return qualificationsList;
    }

    public void setQualificationsList(List<RoleQualification> qualificationsList) {
        this.qualificationsList = qualificationsList;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.project);
        hash = 37 * hash + Objects.hashCode(this.role);
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
        final ProjectRole other = (ProjectRole) obj;
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        return true;
    }
   
    
        
    
}
