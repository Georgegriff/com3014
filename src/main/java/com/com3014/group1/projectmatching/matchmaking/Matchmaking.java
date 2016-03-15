/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.matchmaking;


import com.com3014.group1.projectmatching.model.Skill;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.dto.Role;

import com.com3014.group1.projectmatching.core.services.*;

import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Elliot
 */
@Component
public class Matchmaking {
        
    @Autowired
    private UserService userService;
    
    public Matchmaking(){
    }
    
    // generate random number between min and max
    private static double randDouble(double min, double max) {
        Random rand = new Random();
        double randomNumber = min + (max - min) * rand.nextDouble();
        return randomNumber;
    }
    
    
    // Calculate the fitness score for a particular user to a role
    private double calculateUserScore(User user, Role role) {
        // a list of skills desired but not required for this role
        List<Skill> skillsPreferedForRole = role.getSkillsList(); // <- PLACEHOLDER!

        // get the last login date of the user from the users array
        Date lastLogin = user.getLastLogin();
        int numberDaysSinceLastLogin = (int)((new Date().getTime() - lastLogin.getTime()) / (1000 * 60 * 60 * 24) );
        double loginTimeWeighting = 10.0;

        // calculate the weighting from the dates of last login
        if(numberDaysSinceLastLogin > 10.0) {
            if(numberDaysSinceLastLogin > 300) {
                loginTimeWeighting = 0.0;
            }
        }
        else {
            loginTimeWeighting = 10.0 - ((numberDaysSinceLastLogin / 300.0) * 10.0);
        } 

        int numberDesiredSkillsUserHas = 0;
        
        for (Skill Preferedskill : skillsPreferedForRole) {
            if(user.getSkillsList().contains(Preferedskill)) {
                numberDesiredSkillsUserHas+= 1;
            }
        }
        
        // calculate the weighting from the users skills
        double skillsWeighting = ((numberDesiredSkillsUserHas/skillsPreferedForRole.size()) * 10.0);

        // generate a random weighting
        double randomWeighting = randDouble(0.0, 5.0);

        // calculate the users matchmaking score 
        double userScore = loginTimeWeighting + skillsWeighting + randomWeighting;                  
        return userScore;
    }
    
    
    // Returns an array of user id's, ordered by matchmaking score
    public List<User> findUsersForRole(Role role) {
        // a list of skills required for this role
        List<Skill> skillsRequiredForRole = role.getSkillsList();
        // a map of all users
        Map<Integer, User> allUsersMap = userService.getAllUsers();
        // a map of users already accepted or rejected
        Map<Integer, User> alreadySwipedMap = new HashMap<>(); // <- PLACEHOLDER!
        
        List<User> allUsers = new ArrayList<>(allUsersMap.values());
        List<Integer> alreadySwiped = new ArrayList<>(alreadySwipedMap.keySet());
        Map<Double, User> usersAndScores = new HashMap<>();
        
        for (User user : allUsers) {
            int userID = user.getUserId();
            // check if the user has not already been swiped
            if(false == alreadySwiped.contains(userID)) {
                                
                List<Skill> usersSkills = user.getSkillsList();
                
                if(usersSkills.containsAll(skillsRequiredForRole)) {
                    double userScore = calculateUserScore(user, role);
                    usersAndScores.put(userScore, user);
                }
            }
        }

        // order the users based on matchmaking score
        List<User> usersOrdered = new ArrayList<>();
        
        SortedSet<Double> scores = new TreeSet<>(usersAndScores.keySet());
        for (Double score : scores) { 
           User user = usersAndScores.get(score);
           usersOrdered.add(user);
        }

        return usersOrdered;
    }

    // returns an array of role id's, ordered by score
    public void findRolesForUser() {
    }    
}
