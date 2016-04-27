/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.PasswordDAO;
import com.com3014.group1.projectmatching.dao.UserDAO;
import com.com3014.group1.projectmatching.model.Password;
import com.com3014.group1.projectmatching.model.UserEntity;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sam Waters
 */
@Service
public class PasswordService {

    private static final int MIN_PASSWORD_LENGTH = 5;

    @Autowired
    private PasswordDAO passwordDAO;

    @Autowired
    private UserDAO userDAO;

    public boolean checkPassword(String username, String submittedPassword) {
        if (username == null || submittedPassword == null) {
            return false;
        }

        UserEntity userEntity = userDAO.findByUsername(username);

        if (userEntity != null) {
            try {
                Password userPassword = passwordDAO.findByUserId(userEntity.getUserId());
                String submittedPasswordHash = hashPassword(submittedPassword);
                return userPassword != null && submittedPasswordHash.equals(userPassword.getPassword());
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                Logger.getLogger(PasswordService.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }

    public void changePassword(String username, String oldPassword, String newPassword) throws AuthenticationException, IllegalArgumentException {
        if (!validPassword(newPassword)) {
            // Maybe change to a different type of exception
            throw new IllegalArgumentException("New password is invalid") {
            };
        }
        if (checkPassword(username, oldPassword)) {
            UserEntity userEntity = userDAO.findByUsername(username);
            if (userEntity != null) {
                try {
                    Password userPassword = passwordDAO.findByUserId(userEntity.getUserId());
                    userPassword.setPassword(hashPassword(newPassword));
                    passwordDAO.save(userPassword);
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    Logger.getLogger(PasswordService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            throw new AuthenticationException("Unable to authenticate") {
            };
        }
    }

    public void addPassword(String username, String newPassword) throws AuthenticationException, IllegalArgumentException {
        if (!validPassword(newPassword)) {
            // Maybe change to a different type of exception
            throw new IllegalArgumentException("New password is invalid") {
            };
        }
        UserEntity userEntity = userDAO.findByUsername(username);
        if (userEntity != null) {
            try {
                Password userPassword = passwordDAO.findByUserId(userEntity.getUserId());
                userPassword.setPassword(hashPassword(newPassword));
                passwordDAO.save(userPassword);
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                Logger.getLogger(PasswordService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new AuthenticationException("Unable to authenticate") {
            };
        }
    }

    private boolean validPassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }
        if (password.matches(".*[0-9].*") && (password.matches(".*[A-Z].*") || password.matches(".*[a-z].*"))) {
            return true;
        }
        return false;
    }

    private String hashPassword(String submittedPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(submittedPassword.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        byte[] digest = md.digest();
        return String.format("%064x", new java.math.BigInteger(1, digest));
    }

}
