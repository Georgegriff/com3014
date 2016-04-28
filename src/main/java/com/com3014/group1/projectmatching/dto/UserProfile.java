package com.com3014.group1.projectmatching.dto;

import com.com3014.group1.projectmatching.model.UserInterest;
import com.com3014.group1.projectmatching.model.UserQualification;
import com.com3014.group1.projectmatching.model.UserSkill;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * The definition of a UserProfile
 *
 * @author George
 */
@Repository
public class UserProfile {

    private Integer userId;
    private String name;
    private String forename;
    private String surname;
    private List<UserSkill> skillsList;
    private List<UserQualification> qualificationsList;
    private List<UserInterest> interestsList;
    private Date lastLogin;
    private String email;

    public UserProfile(User user) {
        super();
        this.userId = user.getUserId();
        this.name = user.getName();
        this.forename = user.getForename();
        this.surname = user.getSurname();
        this.skillsList = user.getSkillsList();
        this.qualificationsList = user.getQualificationsList();
        this.interestsList = user.getInterestsList();
        this.lastLogin = user.getLastLogin();
    }

    public UserProfile() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void provideEmail(String email) {
        this.email = email;
    }
}
