/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectDAO;
import com.com3014.group1.projectmatching.model.Project;
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

    public List<Project> getAllProjects() {
        return projectDAO.getAllProjects();
    }

}
