package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Database representation of a @User Interest
 *
 * @author Daniel
 */
@Entity
@Table(name = "user_interests")
public class UserInterest implements Serializable {

    @Id
    @Column(name = "interest_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer interestId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @Column(name = "interest")
    private String interest;

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public UserInterest() {
    }

    public Integer getInterestId() {
        return interestId;
    }

    public void setInterestId(Integer interestId) {
        this.interestId = interestId;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
