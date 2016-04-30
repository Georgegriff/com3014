package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.ProjectService;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.User;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Service for Projects
 *
 * @author George
 */
@RestController
@RequestMapping("/services")
public class RestProjectService {

    @Autowired
    private ProjectService projectService;

    /**
     * Get the Project from the ID
     *
     * @param id The ID of the Project
     * @param session The session used to obtain the current user
     * @param roles Whether to get the roles
     * @param interest Whether to get the interests
     * @return The Project
     */
    @RequestMapping(value = "/project/{id}", params = {"roleInfo", "interestInfo"}, headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getProject(@PathVariable String id, HttpSession session, @RequestParam("roleInfo") boolean roles, @RequestParam("interestInfo") boolean interest) {
        Project project = projectService.getProject(Integer.parseInt(id), getCurrentUser(session), roles, interest);
        if (project != null) {
            return new ResponseEntity<Object>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Insufficient Permissions.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get the Project from the ID
     *
     * @param id The ID of the Project
     * @param session The session used to obtain the current user
     * @return The Project
     */
    @RequestMapping(value = "/project/{id}", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getProject(@PathVariable String id, HttpSession session) {
        Project project = projectService.getProject(Integer.parseInt(id), getCurrentUser(session), true, true);
        if (project != null) {
            return new ResponseEntity<Object>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Insufficient Permissions.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all of the Projects that the User owns
     *
     * @param session The session used to obtain the current user
     * @return The list of Projects that the User owns
     */
    @RequestMapping(value = "/user/projects", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getProjectsUserOwns(HttpSession session) {
        List<Project> projects = projectService.getProjectsUserOwns(getCurrentUser(session));
        if (projects != null) {
            return new ResponseEntity<Object>(projects, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Could Not Retrieve Projects.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Save a Project to the database
     *
     * @param userId The ID of the Project
     * @param project The Project to save
     */
    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/projects/create", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public void createProject(@PathVariable String userId, @RequestBody final Project project) {
        boolean valid = projectService.saveProject(project);
    }

    /**
     * Update a Project to the database
     *
     * @param userId The ID of the Project
     * @param project The Project to save
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{userId}/projects/update", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public void updateProject(@PathVariable String userId, @RequestBody final Project project) {
        boolean valid = projectService.saveProject(project);
    }

    /**
     * Get the current user from the session
     *
     * @param session The session
     * @return The current user
     */
    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("currentUser");
    }
}
