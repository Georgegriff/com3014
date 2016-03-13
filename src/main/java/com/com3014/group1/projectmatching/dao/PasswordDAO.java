/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.Password;
import com.com3014.group1.projectmatching.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface PasswordDAO extends JpaRepository<Password, Integer> {
}
