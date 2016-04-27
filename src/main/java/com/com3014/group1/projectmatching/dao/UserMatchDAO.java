package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Project matches of a User
 *
 * @author Daniel
 */
public interface UserMatchDAO extends JpaRepository<UserMatch, Integer> {

    @Query(
            value = "SELECT * FROM user_matches um WHERE um.user_id = ?1 AND um.status_control = 'C'",
            nativeQuery = true
    )
    public UserMatch findByUser(UserEntity user);

}
