/*
 * Manages Database interactions for Users
 */
package com.com3014.group1.projectmatching.dto;

import com.com3014.group1.projectmatching.model.Location;
import com.com3014.group1.projectmatching.model.UserQualification;
import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserInterest;
import com.com3014.group1.projectmatching.model.UserSkill;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sam Waters
 * @author Dan Ashworth
 */
@Repository
public class User {
        
    private Integer userId;
    private String username;
    private String name;
    private String forename;
    private String surname;
    private String email;
    private Float averageRating;
    private List<UserSkill> skillsList;
    private List<UserQualification> qualificationsList;
    private List<UserInterest> interestsList;
    private Date lastLogin;
    private Location location;

    public User() {
        super();
    }
    
    public User(UserEntity user, List<UserSkill> skillList, List<UserQualification> qualificationList, List<UserInterest> interests) {
        super();
        
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.name = constructName(user);
        this.forename = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.averageRating = user.getAverageRating();
        this.skillsList = skillList;
        this.qualificationsList = qualificationList;
        this.interestsList = interests;
        this.lastLogin = user.getLastLogin();
        this.location = user.getLocation();
    }
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public List<UserSkill> getSkillsList() {
        return skillsList;
    }

    public void setSkillsList(List<UserSkill> skillsList) {
        this.skillsList = skillsList;
    }

    public List<UserQualification> getQualificationsList() {
        return qualificationsList;
    }

    public void setQualificationsList(List<UserQualification> qualificationsList) {
        this.qualificationsList = qualificationsList;
    }

    public List<UserInterest> getInterestsList() {
        return interestsList;
    }

    public void setInterestsList(List<UserInterest> interestsList) {
        this.interestsList = interestsList;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
       
    private String constructName(UserEntity user) {
        return user.getName() + ' ' + user.getSurname();
    }
}
