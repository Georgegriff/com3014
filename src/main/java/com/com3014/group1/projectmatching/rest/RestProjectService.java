package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.ProjectService;
import com.com3014.group1.projectmatching.model.Project;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestProjectService {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/project/{id}", headers = "Accept=application/json")
    public Project getProject(@PathVariable String id) {
        return projectService.getProject(Integer.parseInt(id));
    }

    @RequestMapping(value = "/user/{userId}/projects", headers = "Accept=application/json")
    public List<Project> getProjectsForUser(@PathVariable String userId) {
        return projectService.getProjectsForUser(Integer.parseInt(userId));
    }
}
