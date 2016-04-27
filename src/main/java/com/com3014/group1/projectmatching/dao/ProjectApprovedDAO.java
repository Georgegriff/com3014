package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectApproved;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Approved Projects
 *
 * @author Daniel
 */
public interface ProjectApprovedDAO extends JpaRepository<ProjectApproved, ProjectApproved.ProjectApprovedPK> {

    public List<ProjectApproved> findByProject(ProjectEntity project);

    public List<ProjectApproved> findByUser_UserId(int userId);
}
