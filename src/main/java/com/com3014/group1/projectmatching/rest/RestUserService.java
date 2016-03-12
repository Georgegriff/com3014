/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.UserService;
import com.com3014.group1.projectmatching.model.User;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author George
 */
@RestController
@RequestMapping("/services")
public class RestUserService {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{id}", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable String id) {
        return userService.getUser(Integer.parseInt(id));
    }

    @RequestMapping(value = "/userinfo", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public User getCurrentUser(HttpSession session) {
    return (User) session.getAttribute("currentUser");
    }
}
