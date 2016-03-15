/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.UserDAO;
import com.com3014.group1.projectmatching.dao.UserInterestDAO;
import com.com3014.group1.projectmatching.dao.UserQualificationDAO;
import com.com3014.group1.projectmatching.dao.UserSkillDAO;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserInterest;
import com.com3014.group1.projectmatching.model.UserQualification;
import com.com3014.group1.projectmatching.model.UserSkill;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sam Waters
 * @author Dan Ashworth
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserSkillDAO userSkillDAO;

    @Autowired
    private UserQualificationDAO userQualDAO;

    @Autowired
    private UserInterestDAO userInterestDAO;

    public User getUser(int id) {
        User user;

        try {
            // Get the user entity from the db
            UserEntity userEntity = userDAO.findOne(id);
            // Convert to actual user object
            user = convertEntityToUser(userEntity);
        } catch (ObjectNotFoundException onf) {
            user = null;
            onf.printStackTrace();
        }
        return user;
    }

    /*
     * TODO Elliot, I made this a map as per your implementation but dont know
     * if you want to just return the list of users here and map in the matchmaking?
     */
    public Map<Integer, User> getAllUsers() {
        Map<Integer, User> usersMap = new HashMap<Integer, User>();
        try {
            // Find all the users
            List<UserEntity> allUsers = userDAO.findAll();
            for (int i = 0; i < allUsers.size(); i++) {
                User user = convertEntityToUser(allUsers.get(i));
                usersMap.put(user.getUserId(), user);
            }
        } catch (ObjectNotFoundException onf) {
            usersMap = null;
            onf.printStackTrace();
        }
        return usersMap;
    }

    public User getUserByUsername(String userName) {
        User user;

        try {
            UserEntity userEntity = userDAO.findByUsername(userName);
            user = convertEntityToUser(userEntity);
        } catch (ObjectNotFoundException onf) {
            user = null;
        }

        return user;
    }

    /* Convert a UserEntity from the database to a User Object for the front end */
    private User convertEntityToUser(UserEntity entity) throws ObjectNotFoundException {

        if (entity != null) {
            // Get the attributes of the user
            List<UserSkill> userSkills = userSkillDAO.findByUser(entity);

            List<UserSkill> skills = new ArrayList<UserSkill>();
            for (UserSkill skill : userSkills) {
                skills.add(skill);
            }
            List<UserQualification> userQualifications = userQualDAO.findByUser(entity);
            List<UserInterest> userInterets = userInterestDAO.findByUser(entity);
            // Convert the DAO UserEntity to the DTO User object
            return new User(entity, skills, userQualifications, userInterets);
        } else {
            throw new ObjectNotFoundException(entity, "User Not Found");
        }

    }

}
