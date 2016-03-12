/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.matchmaking;

import com.com3014.group1.projectmatching.model.Location;
import com.com3014.group1.projectmatching.model.Qualification;
import com.com3014.group1.projectmatching.model.Skill;
import com.com3014.group1.projectmatching.model.User;
import com.com3014.group1.projectmatching.model.Role;

import com.com3014.group1.projectmatching.core.services.*;

import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Elliot
 */
public class Matchmaking {
    
    
    public Matchmaking(){
    }
    
    
    // generate random number between min and max
    public static double randDouble(double min, double max) {
        Random rand = new Random();
        double random_number = min + (max - min) * rand.nextDouble();
        return random_number;
    }
    
    
    // Calculate the fitness score for a particular user to a role
    public double calculateUserScore(User user, Role role) {
        // a list of skills desired but not required for this role
        List<Skill> skillsPreferedForRole = role.getSkills(); // <- PLACEHOLDER!

        // get the last login date of the user from the users array
        Date last_login = new Date(); // <- PLACEHOLDER!
        int number_days_since_last_login = (int)((new Date().getTime() - last_login.getTime()) / (1000 * 60 * 60 * 24) );
        double login_time_weighting = 10.0;

        // calculate the weighting from the dates of last login
        if(number_days_since_last_login > 10.0) {
            if(number_days_since_last_login > 300) {
                login_time_weighting = 0.0;
            }
        }
        else {
            login_time_weighting = 10.0 - ((number_days_since_last_login / 300.0) * 10.0);
        } 

        int numberDesiredSkillsUserHas = 0;
        
        for (Skill Preferedskill : skillsPreferedForRole) {
            if(user.getSkills().contains(Preferedskill)) {
                numberDesiredSkillsUserHas+= 1;
            }
        }
        
        // calculate the weighting from the users skills
        double skills_weighting = ((numberDesiredSkillsUserHas/skillsPreferedForRole.size()) * 10.0);

        // generate a random weighting
        double random_weighting = randDouble(0.0, 5.0);

        // calculate the users matchmaking score 
        double userScore = login_time_weighting + skills_weighting + random_weighting;                  
        return userScore;
    }
    
    
    // Returns an array of user id's, ordered by matchmaking score
    List<User> findUsersForRole(Role role) {
        // a list of skills required for this role
        List<Skill> skillsRequiredForRole = role.getSkills();
        // the user service
        UserService service = new UserService();
        // a map of all users
        Map<Integer, User> allUsersMap = service.getAllUsers();
        // a map of users already accepted or rejected
        Map<Integer, User> alreadySwipedMap = new HashMap<>(); // <- PLACEHOLDER!
        
        List<User> allUsers = new ArrayList<>(allUsersMap.values());
        List<Integer> alreadySwiped = new ArrayList<>(alreadySwipedMap.keySet());
        Map<Double, User> usersAndScores = new HashMap<>();
        
        for (User user : allUsers) {
            int userID = user.getUserId();
            // check if the user has not already been swiped
            if(false == alreadySwiped.contains(userID)) {
                                
                List<Skill> usersSkills = user.getSkills();
                
                if(true == usersSkills.containsAll(skillsRequiredForRole)) {
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
    void find_roles_for_user() {
    
    
    }
    
    
}
