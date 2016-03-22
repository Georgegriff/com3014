/*
 * Manages databse interactions for Projects
 */
package com.com3014.group1.projectmatching.dto;

import com.com3014.group1.projectmatching.model.Location;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectInterest;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sam Waters
 * @author Dan Ashworth
 */
@Repository
public class Project {
        
    private Integer projectId;
    private String name;
    private String description;
    private Date projectStart;
    private Date estimatedEnd;
    private Location location;
    private int projectOwner;
    private List<Role> rolesList;
    private List<ProjectInterest> interestsList;
    
    public Project() {
        super();
    }
        
    public Project(ProjectEntity entity){
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

    public List<Role> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Role> rolesList) {
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
}
