/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.repositories;

import com.com3014.group1.projectmatching.model.User;
import com.com3014.group1.projectmatching.model.UserMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Daniel
 */
public interface UserMatchRepository extends JpaRepository<UserMatch, Integer>{
    
    @Query(
        value = "SELECT * FROM user_matches um WHERE um.user_id = ?1 AND um.status_control = 'C'",
        nativeQuery = true
    )
    public UserMatch findByUser(User user);
    
}
