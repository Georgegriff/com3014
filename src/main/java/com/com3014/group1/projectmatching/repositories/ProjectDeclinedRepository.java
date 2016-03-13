/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.repositories;

import com.com3014.group1.projectmatching.model.Project;
import com.com3014.group1.projectmatching.model.ProjectDeclined;
import com.com3014.group1.projectmatching.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface ProjectDeclinedRepository extends JpaRepository<ProjectDeclined, ProjectDeclined.ProjectDeclinedPK>{
    public List<User> findByProject(Project project);
}
