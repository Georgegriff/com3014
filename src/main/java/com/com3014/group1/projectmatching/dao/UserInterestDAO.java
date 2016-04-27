package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserInterest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Interests relating to a User
 *
 * @author Daniel
 */
public interface UserInterestDAO extends JpaRepository<UserInterest, Integer> {

    public List<UserInterest> findByUser(UserEntity user);
}
