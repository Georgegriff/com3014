/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.dto.User;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author George
 */
@RestController
@RequestMapping("/services")
public class RestUserService {

    @RequestMapping(value = "/user", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public User getUser(HttpSession session) {
        return (User) session.getAttribute("currentUser");
    }
}
