/*
 * Controller for Static Paths in the Application
 */
package com.com3014.group1.projectmatching.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author George
 */
@Controller
public class RootController {

    @RequestMapping(value = "/", headers = "Accept=text/html")
    String homePage() {
        return "index";
    }
    
    @RequestMapping(value = "/user/{id}", headers = "Accept=text/html")
    public String userProfilePage(@PathVariable String id) {
        return "index";
    }

    @RequestMapping(value = "/project/{id}", headers = "Accept=text/html")
    public String getProject(@PathVariable String id) {
        return "index";
    }
    @RequestMapping(value = "/user/{id}/projects", headers = "Accept=text/html")
    public String getUserProjects(@PathVariable String id) {
        return "index";
    }

}
