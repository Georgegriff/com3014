package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectRoleEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Project Roles within a Project
 *
 * @author Daniel
 */
public interface ProjectRoleDAO extends JpaRepository<ProjectRoleEntity, Integer> {

    public List<ProjectRoleEntity> findByProject(ProjectEntity project);

    public ProjectRoleEntity findByProject_ProjectIdAndRole_RoleId(int projectId, int roleId);
}
