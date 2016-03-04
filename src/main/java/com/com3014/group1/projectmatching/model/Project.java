/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * The definition of a project
 *
 * @author Sam Waters
 */
@Component
public class Project {

    private int projectId;
    private String name;
    private String description;
    private Date projectStart;
    private Date estimatedEnd;
    private Location location;
    private List<Role> roles;
    private List<String> interests;

    public Project() {
    }

    public Project(int projectId, String name, String description, Date projectStart,
            Date estimatedEnd, Location location, List<Role> roles, List<String> interests) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.projectStart = projectStart;
        this.estimatedEnd = estimatedEnd;
        this.location = location;
        this.roles = roles;
        this.interests = interests;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

}
