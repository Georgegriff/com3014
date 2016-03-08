/*
 * Controller for Static Paths in the Application
 */
package com.com3014.group1.projectmatching.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author George
 */
@Controller
public class RootController {

    @RequestMapping(value = "/", headers ="Accept=" + MediaType.TEXT_HTML_VALUE)
    String homePage() {
        return "index";
    }
    
    @RequestMapping(value = "/user/{id}", headers = "Accept=" + MediaType.TEXT_HTML_VALUE)
    public String userProfilePage(@PathVariable String id) {
        return "index";
    }

    @RequestMapping(value = "/project/{id}", headers = "Accept=" + MediaType.TEXT_HTML_VALUE)
    public String getProject(@PathVariable String id) {
        return "index";
    }
    @RequestMapping(value = "/user/{id}/projects", headers = "Accept="+ MediaType.TEXT_HTML_VALUE)
    public String getUserProjects(@PathVariable String id) {
        return "index";
    }

}
