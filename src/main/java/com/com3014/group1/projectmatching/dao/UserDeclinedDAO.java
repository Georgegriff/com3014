package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserDeclined;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Declined Users
 *
 * @author Daniel
 */
public interface UserDeclinedDAO extends JpaRepository<UserDeclined, UserDeclined.UserDeclinedPK> {

    public List<ProjectEntity> findByUser(UserEntity user);

    public List<UserDeclined> findByProject_ProjectId(int projectId);
}
