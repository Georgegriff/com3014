/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.matchmaking;

import java.util.Date;
import java.util.Random;
import java.util.Arrays;
import java.util.Comparator;

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
    
    
   
    // returns an array of user id's, ordered by matchmaking score
    int[] find_users_for_role(int role_id) {
    
        // get the skills required by the role from the database
        int[] role_skills_required = new int [10];      
        // get the skills desired but not required by the role from the database
        int[] role_skills_desired = new int [10];
        
        
        // an array of all user id's and last login dates
        int[][] users = new int [100][2];
        
        // an array of user id's already swiped for this role
        int[] users_already_swiped = new int[100];
        
        int number_of_users_met_criteria = 0; 

        // the users who meet the criteria, in the form [user_id, matchmaking_score]
        double[][] array_users_met_criteria = new double [100][2];
        
        
        for(int i = 0; i < users.length; i++) {
        
            boolean user_already_swiped = false;
            int user_id = users[i][0];
            
            // disregard users who have already been swiped for this role
            for (int user_swiped : users_already_swiped) {
                if(user_swiped == user_id) {
                    user_already_swiped = true;
                }
            }
            
            if(false == user_already_swiped) {
            
                boolean user_meets_criteria = true;

                // get the skills possesed by the chosen user from the database
                int[] users_skills = new int [10];

                // check that the user has all the skills required
                for(int j = 0; j < role_skills_required.length; j++) {

                    boolean user_has_skill = false;

                    for(int k = 0; k < users_skills.length; k++) {
                        if(users_skills[k] == role_skills_required[k]) {
                            user_has_skill = true;
                        }
                    }

                    if(false == user_has_skill) {
                        user_meets_criteria = false;
                    }
                }

                // if they do add them to the list of matches
                if(true == user_meets_criteria) {

                    int number_desired_skills_user_has = 0;
                    int total_number_desired_skills = role_skills_desired.length;

                    // check how many desired skills the user posseses
                    for(int j = 0; j < role_skills_desired.length; j++) {
                        for(int k = 0; k < users_skills.length; k++) {
                            if(users_skills[k] == role_skills_desired[k]) {
                                number_desired_skills_user_has += 1;
                            }
                        }
                    }
                    
                    // get the last login date of the user from the users array
                    Date last_login = new Date(); // Date last_login = users[i][1]

                    double matchmaking_score = 0;
                    int number_days_since_last_login = (int)((new Date().getTime() - last_login.getTime()) / (1000 * 60 * 60 * 24) );
                    double login_time_weighting = 10.0;

                    // calculate the weighting from the dates of last login
                    if(number_days_since_last_login > 10.0) {
                        if(number_days_since_last_login > 300) {
                            login_time_weighting = 0.0;
                        }
                        else {
                            login_time_weighting = 10.0 - ((number_days_since_last_login / 300.0) * 10.0);
                        } 
                    }

                    // calculate the weighting from the users skills
                    double skills_weighting = 0.0;
                    skills_weighting = ((number_desired_skills_user_has/total_number_desired_skills) * 10.0);

                    // generate a random weighting
                    double random_weighting = randDouble(0.0, 5.0);

                    // calculate the users matchmaking score 
                    matchmaking_score = login_time_weighting + skills_weighting + random_weighting;

                    array_users_met_criteria[number_of_users_met_criteria][0] = user_id;
                    array_users_met_criteria[number_of_users_met_criteria][1] = matchmaking_score;       
                    number_of_users_met_criteria += 1;
                }
            }
        }
        
        // order the users based on matchmaking score
        Arrays.sort(array_users_met_criteria, new Comparator<double[]>() {
            @Override
            public int compare(double[] s1, double[] s2) {
                if (s1[1] > s2[1])
                    return -1;    // tells Arrays.sort() that s1 comes before s2
                else if (s1[1] < s2[1])
                    return +1;   // tells Arrays.sort() that s1 comes after s2
                else {
                    //s1 and s2 are equal
                    return 0;
                }
            }
        });
        
        // place the user id's into a single array to return
        int[] sorted_user_ids = new int[number_of_users_met_criteria];
        
        for (int l =0; l < number_of_users_met_criteria; l++) {  
            sorted_user_ids[l] = (int)array_users_met_criteria[l][0];
        }
        
        return sorted_user_ids;
    }
    
    
    
    // returns an array of role id's, ordered by score
    void find_roles_for_user() {
    
    
    }
    
    
}
