/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.Location;
import com.com3014.group1.projectmatching.model.Qualification;
import com.com3014.group1.projectmatching.model.Skill;
import com.com3014.group1.projectmatching.model.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sam Waters
 */
@Repository
public class UserDAO {

    public List<User> getAllUsers() {

        List<Skill> skills = new ArrayList<Skill>();
        skills.add(new Skill("Java", 12));
        skills.add(new Skill("C++", 12));

        List<Qualification> qualifications = new ArrayList<Qualification>();
        qualifications.add(new Qualification("Computer Sceince", "Degree"));
        qualifications.add(new Qualification("Maths", "A Level"));

        User user1 = new User();
        user1.setUserId(1);
        user1.setName("Bob");
        user1.setSurname("Smith");
        user1.setEmail("bob@smith.com");
        user1.setUsername("BobSmith");
        user1.setAverageRating(5);
        user1.setLocation(new Location(51.50, -0.12));
        user1.setSkills(skills);
        user1.setQualifications(qualifications);
        user1.setInterests(new ArrayList<String>(Arrays.asList(new String[]{"Game Design", "Tennis"})));

        List<Skill> skills2 = new ArrayList<Skill>();
        skills2.add(new Skill("Project Management", 12));
        skills2.add(new Skill("Web Design", 12));

        List<Qualification> qualifications2 = new ArrayList<Qualification>();
        qualifications2.add(new Qualification("Computer Sceince", "Degree"));
        qualifications2.add(new Qualification("Maths", "A Level"));

        User user2 = new User();
        user2.setUserId(2);
        user2.setName("Fred");
        user2.setSurname("Bloggs");
        user2.setEmail("fred@bloggs.com");
        user2.setUsername("FredBloggs");
        user2.setAverageRating(5);
        user2.setLocation(new Location(51.50, -0.12));
        user2.setSkills(skills2);
        user2.setQualifications(qualifications2);
        user2.setInterests(new ArrayList<String>(Arrays.asList(new String[]{"Web Design", "Networking"})));

        List<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);

        return userList;
    }

}
