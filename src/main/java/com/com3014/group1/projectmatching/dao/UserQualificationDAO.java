package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserQualification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Qualifications relating to a User
 *
 * @author Daniel
 */
public interface UserQualificationDAO extends JpaRepository<UserQualification, UserQualification.UserQualificationPK> {

    public List<UserQualification> findByUser(UserEntity user);
}
