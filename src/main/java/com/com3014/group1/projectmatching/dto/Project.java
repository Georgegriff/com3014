package com.com3014.group1.projectmatching.dto;

import com.com3014.group1.projectmatching.model.Location;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectInterest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The definition of a Project
 *
 * @author Sam Waters
 * @author Dan Ashworth
 */
public class Project {

    private Integer projectId;
    private String name;
    private String description;
    private Date projectStart;
    private Date estimatedEnd;
    private Location location;
    private int projectOwner;
    private List<ProjectRole> rolesList;
    private List<ProjectInterest> interestsList;

    public Project(ProjectEntity entity) {
        super();
        this.projectId = entity.getProjectId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.projectStart = entity.getProjectStart();
        this.estimatedEnd = entity.getEstimatedEnd();
        this.location = entity.getLocation();
        this.projectOwner = entity.getProjectOwner().getUserId();
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getProjectStart() {
        return projectStart;
    }

    public void setProjectStart(Date projectStart) {
        this.projectStart = projectStart;
    }

    public Date getEstimatedEnd() {
        return estimatedEnd;
    }

    public void setEstimatedEnd(Date estimatedEnd) {
        this.estimatedEnd = estimatedEnd;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<ProjectRole> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<ProjectRole> rolesList) {
        this.rolesList = rolesList;
    }

    public List<ProjectInterest> getInterestsList() {
        return interestsList;
    }

    public void setInterestsList(List<ProjectInterest> interestsList) {
        this.interestsList = interestsList;
    }

    public int getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(int projectOwner) {
        this.projectOwner = projectOwner;
    }

    /**
     * Overridden because a payment is equal if it's fields are equal, not
     * objects
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.projectId);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.projectStart);
        hash = 79 * hash + Objects.hashCode(this.estimatedEnd);
        hash = 79 * hash + Objects.hashCode(this.location);
        hash = 79 * hash + this.projectOwner;
        hash = 79 * hash + Objects.hashCode(this.rolesList);
        hash = 79 * hash + Objects.hashCode(this.interestsList);
        return hash;
    }

    /**
     * Overridden because a payment is equal if it's fields are equal, not
     * objects
     *
     * @param obj The object to compare this one to
     * @return Whether the given object's fields are the same as this one
     */
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
        final Project other = (Project) obj;
        if (this.projectOwner != other.projectOwner) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.projectId, other.projectId)) {
            return false;
        }
        if (!Objects.equals(this.projectStart, other.projectStart)) {
            return false;
        }
        if (!Objects.equals(this.estimatedEnd, other.estimatedEnd)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.rolesList, other.rolesList)) {
            return false;
        }
        if (!Objects.equals(this.interestsList, other.interestsList)) {
            return false;
        }
        return true;
    }

}
