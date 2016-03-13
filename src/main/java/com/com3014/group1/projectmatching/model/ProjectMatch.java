/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import java.util.Date;
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
 *
 * @author Daniel
 */
@Entity
@Table(name = "project_matches")
public class ProjectMatch implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="set_id")
    private Integer setId;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name ="project_id")
    private Project project;
    
    @NotNull
    @Column (name = "cache_expire")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cacheExpire;
    
    @Column (name = "status_control")
    private String statusControl;
    
    public ProjectMatch() {
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public void setProject(Project project) {
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

    public Project getProject() {
        return project;
    }
}
