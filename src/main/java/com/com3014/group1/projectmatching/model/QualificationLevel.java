/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "qualification_levels")
public class QualificationLevel implements Serializable {
    
    @Id
    @Column(name="qualification_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer qualificationId;
    
    @NotNull
    @Column(name="qualification_level")
    private String qualificationLevel;
    
    public QualificationLevel() {
    }

    public Integer getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(Integer qualificationId) {
        this.qualificationId = qualificationId;
    }

    public String getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(String qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    } 
}