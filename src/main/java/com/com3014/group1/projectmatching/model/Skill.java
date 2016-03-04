/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

/**
 *
 * @author Sam Waters
 */
public class Skill {

    private String name;
    private int monthsOfExperience;

    public Skill() {
    }

    public Skill(String name, int monthsOfExperience) {
        this.name = name;
        this.monthsOfExperience = monthsOfExperience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonthsOfExperience() {
        return monthsOfExperience;
    }

    public void setMonthsOfExperience(int monthsOfExperience) {
        this.monthsOfExperience = monthsOfExperience;
    }

}
