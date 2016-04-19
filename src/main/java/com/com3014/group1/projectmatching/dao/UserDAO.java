/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.UserEntity;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface UserDAO extends JpaRepository<UserEntity, Integer>{
    // Query to get User object from username
    public UserEntity findByUsername(String username) throws ObjectNotFoundException;
    public UserEntity findById(int id) throws ObjectNotFoundException;
}
