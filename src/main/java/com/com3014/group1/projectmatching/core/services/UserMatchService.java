/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectApprovedDAO;
import com.com3014.group1.projectmatching.dao.ProjectDeclinedDAO;
import com.com3014.group1.projectmatching.dao.UserMatchDAO;
import com.com3014.group1.projectmatching.dao.UserSetDAO;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.model.ProjectApproved;
import com.com3014.group1.projectmatching.model.ProjectDeclined;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserMatch;
import com.com3014.group1.projectmatching.model.UserSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel
 */
@Service
public class UserMatchService {
    
    @Autowired
    private UserMatchDAO userMatchDAO;
    
    @Autowired
    private UserSetDAO  userSetDAO;
    
    @Autowired
    private ProjectApprovedDAO projectApprovedDAO;
    
    @Autowired
    private ProjectDeclinedDAO projectDeclinedDAO;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserService userService;
    
    /* This method is public as according to the docs to properly implement transactional 
     * it needs to be public for hibernate to proxy the method as a transaction
     */
    @Transactional
    public void saveMatchesForUser(UserEntity userEntity, List<Project> projects) {
               
        // Try to get the user match row from the db
        UserMatch userMatchEntity = this.userMatchDAO.findByUser(userEntity);
        // If there isn't one create a new one
        if(userMatchEntity == null) {
            userMatchEntity = new UserMatch();
        }
        // Set relevant params        
        userMatchEntity.setUser(userEntity);
        userMatchEntity.setCacheExpire(setCacheExpireTime());
        userMatchEntity.setStatusControl("C");
        this.userMatchDAO.save(userMatchEntity);

        // Remove previous set from database before adding new ones
        userSetDAO.deleteBySet(userMatchEntity);
        // Loop over all the projects the user matches too and save to the database
        for(int i = 0; i < projects.size(); i++) {
            UserSet userSetEntity = new UserSet();
            userSetEntity.setSet(userMatchEntity);
            userSetEntity.setProject(projectService.convertProjectToEntity(projects.get(i)));
            userSetDAO.save(userSetEntity);
            // Does a bulk insert as opposed to numerous individual inserts
            if(i % 5 == 0) {
                userSetDAO.flush();
            }
        }
    }
    
    public List<Project> retrieveCachedMatches(UserMatch userMatch){
        List<UserSet> matches = this.userSetDAO.findBySet(userMatch);
        List<ProjectEntity> projects = new ArrayList<>();
        
        for(UserSet match : matches) {
            projects.add(match.getProject());
        }
        return this.projectService.convertEntityListToProjectList(projects);
    }
    
    public UserMatch findMatchForUser(UserEntity entity) {
        return this.userMatchDAO.findByUser(entity);
    }
    
    private Date setCacheExpireTime() {
        Date date = new Date();
        Calendar c = Calendar.getInstance(); 
        c.setTime(date); 
        // Add one day to the current date as the cache expire time
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return date;
    }
    
    @Transactional
    public void saveAcceptedProjects(int userId, List<String> acceptedProjects) {
        List<ProjectApproved> projectsApproved = new ArrayList<>();
        
        for(String project : acceptedProjects) {
            ProjectEntity projectEntity = this.projectService.findProjectById(Integer.parseInt(project));
            UserEntity userEntity = this.userService.findEntityById(userId);
            ProjectApproved approvedEntity = new ProjectApproved();
            approvedEntity.setProject(projectEntity);
            approvedEntity.setUser(userEntity);
            projectsApproved.add(approvedEntity);
        }
        this.projectApprovedDAO.save(projectsApproved);
    }
    
    @Transactional
    public void saveRejectedProjects(int userId, List<String> rejectedProjects) {
        List<ProjectDeclined> projectsRejected = new ArrayList<>();
        
        for(String project : rejectedProjects) {
            ProjectEntity projectEntity = this.projectService.findProjectById(Integer.parseInt(project));
            UserEntity userEntity = this.userService.findEntityById(userId);
            ProjectDeclined declinedEntity = new ProjectDeclined();
            declinedEntity.setProject(projectEntity);
            declinedEntity.setUser(userEntity);
            projectsRejected.add(declinedEntity);
        }
        this.projectDeclinedDAO.save(projectsRejected);
    }      
}
