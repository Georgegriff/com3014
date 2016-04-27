package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.UserEntity;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Users
 *
 * @author Daniel
 */
public interface UserDAO extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsername(String username) throws ObjectNotFoundException;

    public UserEntity findByUserId(int id) throws ObjectNotFoundException;
}
