package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.ProjectService;
import com.com3014.group1.projectmatching.dto.Project;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class RestProjectService {

    @Autowired
    private ProjectService projectService;
    
    @RequestMapping(value = "/project/{id}", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public Project getProject(@PathVariable String id) {
        return projectService.getProject(Integer.parseInt(id));
    }

    @RequestMapping(value = "/user/{userId}/projects", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getProjectsForUser(@PathVariable String userId) {
        return projectService.getProjectsForUser(Integer.parseInt(userId));
    }
     @RequestMapping(method=RequestMethod.POST, value = "/user/{userId}/projects/create", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public void createProject(@PathVariable String userId, @RequestBody final Project project) {
        projectService.createProject(Integer.parseInt(userId), project);
    }
}
