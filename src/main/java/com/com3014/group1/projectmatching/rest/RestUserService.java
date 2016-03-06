/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.UserService;
import com.com3014.group1.projectmatching.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author George
 */
@RestController
public class RestUserService {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{id}", headers = "Accept=application/json")
    public User getUser(@PathVariable String id) {
        return userService.getUser(Integer.parseInt(id));
    }
    @RequestMapping(value = "/userinfo", headers = "Accept=application/json")
    public User getCurrentUser() {
        //TODO:: hard coded to user 1 at the momentT
        return userService.getUser(1);
    }
}
