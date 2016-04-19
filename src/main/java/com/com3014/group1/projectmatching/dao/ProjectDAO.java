/*
 * To change this license header, choose License Headers in ProjectEntity Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

/**
 *
 * @author Daniel
 */
public interface ProjectDAO extends JpaRepository<ProjectEntity, Integer>{
    public List<ProjectEntity> findByProjectOwner(UserEntity projectOwner);
    
    @Modifying
    public void setProjectByProjectOwner(UserEntity projectOwner, ProjectEntity project);
}
