package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Database representation of a Password
 *
 * @author Daniel
 */
@Entity
@Table(name = "passwords")
public class Password implements Serializable {
    
    @Id
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    public Password() {
    }
    
    public Password(Integer userId, String password) {
        this.userId = userId;
        this.password = password;
    }
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
