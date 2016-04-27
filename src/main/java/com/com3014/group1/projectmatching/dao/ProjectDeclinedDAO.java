package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectDeclined;
import com.com3014.group1.projectmatching.model.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Declined Projects
 *
 * @author Daniel
 */
public interface ProjectDeclinedDAO extends JpaRepository<ProjectDeclined, ProjectDeclined.ProjectDeclinedPK> {

    public List<UserEntity> findByProject(ProjectEntity project);

    public List<ProjectDeclined> findByUser_UserId(int userId);
}
