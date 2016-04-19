/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.UserService;
import com.com3014.group1.projectmatching.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sam Waters
 */
@RestController
@RequestMapping("/services/register")
public class RestRegisterService {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/user", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public void registerUser(@PathVariable User user) {
        boolean valid = userService.registerUser(user);
    }
    
}
