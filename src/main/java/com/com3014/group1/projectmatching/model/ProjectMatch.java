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

/**
 * Database representation of a Project Match
 *
 * @author Daniel
 */
@Entity
@Table(name = "project_matches")
public class ProjectMatch implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "set_id")
    private Integer setId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @NotNull
    @Column(name = "cache_expire")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cacheExpire;

    @Column(name = "status_control")
    private String statusControl;

    public ProjectMatch() {
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public Date getCacheExpire() {
        return cacheExpire;
    }

    public void setCacheExpire(Date cacheExpire) {
        this.cacheExpire = cacheExpire;
    }

    public String getStatusControl() {
        return statusControl;
    }

    public void setStatusControl(String statusControl) {
        this.statusControl = statusControl;
    }

    public ProjectEntity getProject() {
        return project;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.setId);
        hash = 17 * hash + Objects.hashCode(this.project);
        hash = 17 * hash + Objects.hashCode(this.cacheExpire);
        hash = 17 * hash + Objects.hashCode(this.statusControl);
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
        final ProjectMatch other = (ProjectMatch) obj;
        if (!Objects.equals(this.statusControl, other.statusControl)) {
            return false;
        }
        if (!Objects.equals(this.setId, other.setId)) {
            return false;
        }
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        if (!Objects.equals(this.cacheExpire, other.cacheExpire)) {
            return false;
        }
        return true;
    }
}
