/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.repositories;

import com.com3014.group1.projectmatching.model.ProjectMatch;
import com.com3014.group1.projectmatching.model.ProjectSet;
import com.com3014.group1.projectmatching.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface ProjectSetRepository extends JpaRepository<ProjectSet, ProjectSet.ProjectSetPK>{
    
    public List<User> findBySet(ProjectMatch set);
    
}
