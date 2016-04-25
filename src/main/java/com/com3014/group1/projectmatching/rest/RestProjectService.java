package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.ProjectService;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.User;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class RestProjectService {

    @Autowired
    private ProjectService projectService;
    
    /* Map for project id with params to decide if role and interets should be pulled */
    @RequestMapping(value = "/project/{id}", params = {"roleInfo", "interestInfo"}, headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public Project getProject(@PathVariable String id, HttpSession session, @RequestParam("roleInfo") boolean roles, @RequestParam("interestInfo") boolean interest) {
        return projectService.getProject(Integer.parseInt(id), getCurrentUser(session), roles, interest);
    }

    /* Default project map that will pull in both interest and roles as well as project info */
    @RequestMapping(value = "/project/{id}", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public Project getProject(@PathVariable String id, HttpSession session) {
        return projectService.getProject(Integer.parseInt(id), getCurrentUser(session), true, true);
    }
    
    @RequestMapping(value = "/user/projects", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getProjectsUserOwns(HttpSession session) {
        return projectService.getProjectsUserOwns(getCurrentUser(session));
    }
    
    @RequestMapping(method=RequestMethod.POST, value = "/user/{userId}/projects/create", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public void createProject(@PathVariable String userId, @RequestBody final Project project) {
        boolean valid = projectService.saveProject(project);
    }
    
    @RequestMapping(method=RequestMethod.PUT, value = "/user/{userId}/projects/update", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public void updateProject(@PathVariable String userId, @RequestBody final Project project) {
        boolean valid = projectService.saveProject(project);
    }
    
    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("currentUser");
    }
}
