/*
 * To change this license header, choose License Headers in ProjectEntity Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
    
    @ManyToOne
    @JoinColumn(name = "project_owner")
    private UserEntity projectOwner;

    public ProjectEntity() {
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        if(projectId.equals(this.projectId)) {
            return;
        }
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.equals(this.name)) {
            return;
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description.equals(this.description)) {
            return;
        }
        this.description = description;
    }

    public Date getProjectStart() {
        return projectStart;
    }

    public void setProjectStart(Date projectStart) {
        if(projectStart.equals(this.projectStart)) {
            return;
        }
        this.projectStart = projectStart;
    }

    public Date getEstimatedEnd() {
        return estimatedEnd;
    }

    public void setEstimatedEnd(Date estimatedEnd) {
        if(estimatedEnd.equals(this.estimatedEnd)) {
            return;
        }
        this.estimatedEnd = estimatedEnd;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if(location.equals(this.location)) {
            return;
        }
        this.location = location;
    }
    
    public UserEntity getProjectOwner() {
        return projectOwner;
    }
    
    public void setProjectOwner(UserEntity projectOwner) {
        if(projectOwner.equals(this.projectOwner)) {
            return;
        }
        this.projectOwner = projectOwner;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.projectId);
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.projectStart);
        hash = 71 * hash + Objects.hashCode(this.estimatedEnd);
        hash = 71 * hash + Objects.hashCode(this.location);
        hash = 71 * hash + Objects.hashCode(this.projectOwner);
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
        final ProjectEntity other = (ProjectEntity) obj;
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
        if (!Objects.equals(this.projectOwner, other.projectOwner)) {
            return false;
        }
        return true;
    }
    
    
}
