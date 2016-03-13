/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.repositories;

import com.com3014.group1.projectmatching.model.Project;
import com.com3014.group1.projectmatching.model.ProjectMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Daniel
 */
public interface ProjectMatchRepository extends JpaRepository<ProjectMatch, Integer> {
    
    @Query(
        value = "SELECT * FROM project_matches pm WHERE pm.project_id = ?1 AND pm.status_control = 'C'",
        nativeQuery = true
    )
    public ProjectMatch findByProject(Project project);
}
