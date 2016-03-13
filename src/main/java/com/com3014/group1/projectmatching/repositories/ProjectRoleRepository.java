/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.repositories;

import com.com3014.group1.projectmatching.model.Project;
import com.com3014.group1.projectmatching.model.ProjectRole;
import com.com3014.group1.projectmatching.model.ProjectRole.ProjectRolePK;
import com.com3014.group1.projectmatching.model.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface ProjectRoleRepository extends JpaRepository<ProjectRole, ProjectRolePK>{
    public List<Role> findByProject(Project project);
}
