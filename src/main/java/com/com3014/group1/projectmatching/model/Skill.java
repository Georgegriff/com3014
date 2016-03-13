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

/**
 *
 * @author Sam Waters
 * @author Dan Ashworth
 */
@Entity
@Table(name = "skills")
public class Skill implements Serializable {

    @Id
    @Column(name="skill_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer skillId;
    
    @Column(name="skill")
    private String name;
    
    public Skill() {
    }

    public Skill(String name) {
        this.name = name;
    }

    public Integer getSkillId() {
        return skillId;
    }
    
    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
