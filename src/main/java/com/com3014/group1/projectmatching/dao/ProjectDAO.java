package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access Projects
 * owned by the User
 *
 * @author Daniel
 */
public interface ProjectDAO extends JpaRepository<ProjectEntity, Integer> {

    public List<ProjectEntity> findByProjectOwner(UserEntity projectOwner);
}
