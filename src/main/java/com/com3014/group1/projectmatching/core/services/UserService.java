/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.UserDAO;
import com.com3014.group1.projectmatching.model.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sam Waters
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
     
    public User getUser(int id){
        return userDAO.findUserById(id);
    }
    
    public Map<Integer, User> getAllUsers() {
        return userDAO.getUserIdMap();
    }
    
    public User getUserByUsername(String userName) {
         return userDAO.findUserByUsername(userName);
    }
   
}
