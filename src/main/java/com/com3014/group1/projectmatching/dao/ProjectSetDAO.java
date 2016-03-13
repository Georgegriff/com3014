/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectMatch;
import com.com3014.group1.projectmatching.model.ProjectSet;
import com.com3014.group1.projectmatching.model.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface ProjectSetDAO extends JpaRepository<ProjectSet, ProjectSet.ProjectSetPK>{
    
    public List<UserEntity> findBySet(ProjectMatch set);
    
}
