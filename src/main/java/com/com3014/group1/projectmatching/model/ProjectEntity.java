/*
 * To change this license header, choose License Headers in ProjectEntity Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;

/**
 * The definition of a project
 *
 * @author Sam Waters
 * @author Dan Ashworth
 */
@Entity
@Table(name = "projects")
public class ProjectEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="project_id")
    private Integer projectId;
    
    @NotNull
    @Column (name = "name")
    private String name;
    
    @NotNull
    @Column (name = "description")
    private String description;
    
    @Temporal(TemporalType.DATE)
    @Column (name = "project_start")
    private Date projectStart;
    
    @Temporal(TemporalType.DATE)
    @Column (name = "estimated_end")
    private Date estimatedEnd;
    
    @Type (type="com.com3014.group1.projectmatching.model.LocationType")
    @Columns(columns = {
        @Column(name="location_lat"),
        @Column(name="location_lon")
        }
    )
    private Location location;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_owner")
    private UserEntity projectOwner;

    public ProjectEntity() {
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
    
    public UserEntity getProjectOwner() {
        return projectOwner;
    }
    
    public void setProjectOwner(UserEntity projectOwner) {
        this.projectOwner = projectOwner;
    }
}
