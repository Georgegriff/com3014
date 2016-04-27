/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.matchmaking;

import com.com3014.group1.projectmatching.model.Location;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.dto.Project;

import com.com3014.group1.projectmatching.core.services.UserService;
import com.com3014.group1.projectmatching.core.services.ProjectService;
import com.com3014.group1.projectmatching.core.services.UserMatchService;
import com.com3014.group1.projectmatching.dto.ProjectRole;
import com.com3014.group1.projectmatching.model.RoleSkill;
import com.com3014.group1.projectmatching.model.UserSkill;

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

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserMatchService userMatchService;

    public Matchmaking() {
    }

    // generate random number between min and max
    private static double randDouble(double min, double max) {
        Random rand = new Random();
        double randomNumber = min + (max - min) * rand.nextDouble();
        return randomNumber;
    }

    // Calculate the fitness score for a particular user to a role
    private double calculateUserScore(User user, ProjectRole role) {
        // a list of skills desired but not required for this role
        List<RoleSkill> roleSkills = role.getSkillsList();

        // get the last login date of the user from the users array
        Date lastLogin = user.getLastLogin();
        int numberDaysSinceLastLogin = (int) ((new Date().getTime() - lastLogin.getTime()) / (1000 * 60 * 60 * 24));
        double loginTimeWeighting = 10.0;

        // calculate the weighting from the dates of last login
        if (numberDaysSinceLastLogin > 10.0) {
            if (numberDaysSinceLastLogin > 300) {
                loginTimeWeighting = 0.0;
            }
        } else {
            loginTimeWeighting = 10.0 - ((numberDaysSinceLastLogin / 300.0) * 10.0);
        }

        int numberDesiredSkillsUserHas = 0;
        int numberDesiredSkillsForRole = 0;

        for (RoleSkill roleSkill : roleSkills) {
            if (!roleSkill.isRequired()) {
                numberDesiredSkillsForRole++;
                List<UserSkill> userSkills = user.getSkillsList();
                for (UserSkill userSkill : userSkills) {
                    if (userSkill.getSkill().getSkillId().intValue() == roleSkill.getSkill().getSkillId().intValue()) {
                        numberDesiredSkillsUserHas += 1;
                    }
                }
            }
        }

        // calculate the weighting from the users skills
        double skillsWeighting = numberDesiredSkillsForRole == 0 ? 0 : ((numberDesiredSkillsUserHas / numberDesiredSkillsForRole) * 10.0);

        // calculate the distance weighting
        Location projectLocation = role.getProject().getLocation();
        Location userLocation = user.getLocation();
      
        double distance = projectLocation.getDistance(userLocation);
        double distanceWeighting = 5.0;
        
        if(distance > 400.0) {
            distanceWeighting = 0.0;
        }
        else if(distance > 10.0) {
            distanceWeighting = ((distance / 400.0) * 5);    
        }

        // generate a random weighting
        double randomWeighting = randDouble(0.0, 10.0);

        // calculate the users matchmaking score 
        double userScore = loginTimeWeighting + skillsWeighting + distanceWeighting + randomWeighting;
        return userScore;
    }

    // Returns an array of user id's, ordered by matchmaking score
    public List<User> findUsersForRole(ProjectRole projectRole) {
        // a list of skills required for this role
        List<RoleSkill> roleSkills = projectRole.getSkillsList();
        // a map of all users
        Map<Integer, User> allUsersMap = userService.getAllUsers();
        // a map of users already accepted or rejected
        Map<Integer, User> alreadySwipedMap = userService.getAlreadySwipedUsers(projectRole.getProject().getProjectId());

        List<User> allUsers = new ArrayList<>(allUsersMap.values());
        List<Integer> alreadySwiped = new ArrayList<>(alreadySwipedMap.keySet());
        Map<Double, User> usersAndScores = new HashMap<>();

        for (User user : allUsers) {
            int userID = user.getUserId();
            // check the user has not already been swiped
            if (!alreadySwiped.contains(userID)) {

                List<UserSkill> userSkills = user.getSkillsList();

                if (userHasRequiredSkills(userSkills, roleSkills)) {
                    double userScore = calculateUserScore(user, projectRole);
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

    private boolean userHasRequiredSkills(List<UserSkill> userSkills, List<RoleSkill> roleSkills) {
        int numberOfRequiredSkills = 0;
        int numberOfRequiredSkillsUserHas = 0;
        for (RoleSkill roleSkill : roleSkills) {
            if (roleSkill.isRequired()) {
                numberOfRequiredSkills++;
                for (UserSkill userSkill : userSkills) {
                    if (userSkill.getSkill().getSkillId().intValue() == roleSkill.getSkill().getSkillId().intValue()) {
                        numberOfRequiredSkillsUserHas += 1;
                    }
                }
            }
        }
        return numberOfRequiredSkills == numberOfRequiredSkillsUserHas;
    }

    // returns an array of projects with matches roles, ordered by score
    public List<ProjectRole> findRolesForUser(User user) {
        // a list of skills this user has
        List<UserSkill> userSkills = user.getSkillsList();
        // a list of all projects
        List<Project> allProjects = projectService.getAllProjects();

        // a map of users already accepted or rejected
        List<ProjectRole> alreadySwipedMap = userMatchService.getAlreadySwipedProjects(user.getUserId());

        //Map<Role, Project> roleIdProjectData = new HashMap<>();
        Map<Double, ProjectRole> projectRolesAndScores = new HashMap<>();
        
        for (Project project : allProjects) {
            // get the projects roles
            List<ProjectRole> allProjectRoles = project.getRolesList();
            
            for (ProjectRole projectRole : allProjectRoles) {
                
                // check the role in this project has not already been swiped
                if (!alreadySwipedMap.contains(projectRole)) {
                       
                    List<RoleSkill> roleSkills = projectRole.getSkillsList();

                    if (userHasRequiredSkills(userSkills, roleSkills)) {
                        double userScore = calculateUserScore(user, projectRole);
                        projectRolesAndScores.put(userScore, projectRole);
                    }
                }
            }
        }

        // order the users based on matchmaking score
        List<ProjectRole> projectRolesOrdered = new ArrayList<>();

        SortedSet<Double> scores = new TreeSet<>(projectRolesAndScores.keySet());
        for (Double score : scores) {
            projectRolesOrdered.add(projectRolesAndScores.get(score));
        }

        return projectRolesOrdered;
    }
}
