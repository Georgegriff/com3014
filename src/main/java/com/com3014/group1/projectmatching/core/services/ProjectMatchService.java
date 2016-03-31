/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectDAO;
import com.com3014.group1.projectmatching.dao.ProjectMatchDAO;
import com.com3014.group1.projectmatching.dao.ProjectSetDAO;
import com.com3014.group1.projectmatching.dao.UserApprovedDAO;
import com.com3014.group1.projectmatching.dao.UserDeclinedDAO;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectMatch;
import com.com3014.group1.projectmatching.model.ProjectSet;
import com.com3014.group1.projectmatching.model.UserApproved;
import com.com3014.group1.projectmatching.model.UserDeclined;
import com.com3014.group1.projectmatching.model.UserEntity;
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
public class ProjectMatchService {
    
    @Autowired
    private ProjectMatchDAO projectMatchDAO;
    
    @Autowired
    private ProjectSetDAO projectSetDAO;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserApprovedDAO userApprovedDAO;
    
    @Autowired
    private UserDeclinedDAO userDeclinedDAO;
    
    public ProjectMatch findMatchForProject(ProjectEntity projectEntity) {
        return this.projectMatchDAO.findByProject(projectEntity);
    }
    
    @Transactional
    public void saveMatchesForProject(ProjectEntity projectEntity, List<User> users) {
       
        ProjectMatch projectMatchEntity = this.projectMatchDAO.findByProject(projectEntity);
        
        if(projectMatchEntity == null) {
            projectMatchEntity = new ProjectMatch();
        }
             
        projectMatchEntity.setProject(projectEntity);
        projectMatchEntity.setCacheExpire(setCacheExpireTime());
        projectMatchEntity.setStatusControl("C");
        this.projectMatchDAO.save(projectMatchEntity);
        
        this.projectSetDAO.deleteBySet(projectMatchEntity);
        for(int i = 0; i < users.size(); i++) {
            ProjectSet projectSetEntity = new ProjectSet();
            projectSetEntity.setSet(projectMatchEntity);
            projectSetEntity.setUser(this.userService.convertUserToEntity(users.get(i)));
            projectSetDAO.save(projectSetEntity);
            // Does a bulk insert as opposed to numerous individual inserts
            if(i % 5 == 0) {
                projectSetDAO.flush();
            }
        }   
    }
    
    public List<User> retrieveCachedMatches(ProjectMatch projectMatch){
        List<ProjectSet> matches = this.projectSetDAO.findBySet(projectMatch);
        List<UserEntity> users = new ArrayList<>();
        
        for(ProjectSet match : matches) {
            users.add(match.getUser());
        }
        return this.userService.convertEntityListToUserList(users);
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
    public void saveAcceptedUsers(int projectId, List<String> acceptedUsers) {
        List<UserApproved> usersApproved = new ArrayList<>();
        
        for(String user : acceptedUsers) {
            ProjectEntity projectEntity = this.projectService.findProjectById(projectId);
            UserEntity userEntity = this.userService.findEntityById(Integer.parseInt(user));
            UserApproved approvedEntity = new UserApproved();
            approvedEntity.setUser(userEntity);
            approvedEntity.setProject(projectEntity);
            usersApproved.add(approvedEntity);
        }
        this.userApprovedDAO.save(usersApproved);
    }
    
    @Transactional
    public void saveRejectedUsers(int projectId, List<String> rejectedUsers) {
        List<UserDeclined> usersDeclined = new ArrayList<>();
        
        for(String user : rejectedUsers) {
            ProjectEntity projectEntity = this.projectService.findProjectById(projectId);
            UserEntity userEntity = this.userService.findEntityById(Integer.parseInt(user));
            UserDeclined declinedEntity = new UserDeclined();
            declinedEntity.setUser(userEntity);
            declinedEntity.setProject(projectEntity);
            usersDeclined.add(declinedEntity);
        }
        this.userDeclinedDAO.save(usersDeclined);
    }      
}
