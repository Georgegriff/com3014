package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserApproved;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Approved Users
 *
 * @author Daniel
 */
public interface UserApprovedDAO extends JpaRepository<UserApproved, UserApproved.UserApprovedPK> {

    public List<UserApproved> findByUser(UserEntity user);

    public List<UserApproved> findByProject_ProjectId(int projectId);
}
