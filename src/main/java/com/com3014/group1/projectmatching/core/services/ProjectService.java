/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectDAO;
import com.com3014.group1.projectmatching.model.Project;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sam Waters
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    public Project getProject(int id) {
         //TODO:: need to ensure project owner and accepted people can view project
        return projectDAO.findProjectById(id);
    }

    public List<Project> getProjectsForUser(int userId) {
        // TODO: dummy implementation at the moment
        int[] projects = {1,2};
        List<Project> userProjects = new ArrayList<>();
        for(int i = 1; i <= projects.length; i++){
            userProjects.add(projectDAO.findProjectById(i));
        }
    
        return userProjects;
    }

    public List<Project> createProject(int userId, Project projectData) {
        //TODO::
        return null;
    }

}
