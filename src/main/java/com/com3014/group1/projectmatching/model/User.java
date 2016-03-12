/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * The definition of a User
 *
 * @author Sam Waters
 */
@Component
public class User {

    private int userId;
    private String username;
    private String name;
    private String surname;
    private String email;
    private float averageRating;
    private Location location;
    private List<Skill> skills;
    private List<Qualification> qualifications;
    private List<String> interests;
    private AccountType accountType = AccountType.INTERNAL;

    /**
     * Empty constructor so fields can be filled with
     */
    public User() {
    }

    public User(int userId, String username, String name, String surname, String email,
            float averageRating, Location location, List<Skill> skills, List<Qualification> qualifications, List<String> interests, AccountType accountType) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.averageRating = averageRating;
        this.location = location;
        this.skills = skills;
        this.qualifications = qualifications;
        this.interests = interests;
        this.accountType = accountType;
    }

    public User(int userId, String username, String name, String surname, String email,
            float averageRating, Location location, List<Skill> skills, List<Qualification> qualifications, List<String> interests) {
       this(userId,username, name, surname, email, averageRating, location, skills, qualifications, interests, AccountType.INTERNAL);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType type) {
        this.accountType = type;
    }
}
