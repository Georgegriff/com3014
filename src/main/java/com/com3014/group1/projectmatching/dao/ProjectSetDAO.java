/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectMatch;
import com.com3014.group1.projectmatching.model.ProjectSet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel
 */
@Transactional(readOnly = true)
public interface ProjectSetDAO extends JpaRepository<ProjectSet, ProjectSet.ProjectSetPK>{
    
    public List<ProjectSet> findBySet(ProjectMatch set);
    
    @Modifying
    @Transactional
    public void deleteBySet(ProjectMatch set);
    
}
