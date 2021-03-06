package com.com3014.group1.projectmatching.dto;

import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.RoleQualification;
import com.com3014.group1.projectmatching.model.RoleSkill;
import java.util.List;
import java.util.Objects;

/**
 * A @Role belonging to a @Project
 *
 * @author Daniel
 */
public class ProjectRole {

    /**
     * The Project that this Role belongs to
     */
    private Project project;

    /**
     * The Role
     */
    private RoleEntity role;

    /**
     * The list of skills related to the Role
     */
    private List<RoleSkill> skillsList;

    /**
     * The list of qualifications related to the Role
     */
    private List<RoleQualification> qualificationsList;

    /**
     * Constructor
     *
     * @param project The Project that this Role belongs to
     * @param role The Role
     * @param roleSkills The list of skills related to the Role
     * @param roleQualifications The list of qualifications related to the Role
     */
    public ProjectRole(Project project, RoleEntity role, List<RoleSkill> roleSkills, List<RoleQualification> roleQualifications) {
        super();
        this.project = project;
        this.role = role;
        this.skillsList = roleSkills;
        this.qualificationsList = roleQualifications;
    }

    /**
     * Constructor
     *
     * @param project The Project that this Role belongs to
     * @param role The Role
     */
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
