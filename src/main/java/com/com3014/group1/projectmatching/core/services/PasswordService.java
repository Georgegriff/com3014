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
import org.springframework.stereotype.Service;

/**
 *
 * @author Sam Waters
 */
@Service
public class PasswordService {

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

    private String hashPassword(String submittedPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(submittedPassword.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        byte[] digest = md.digest();
        return String.format("%064x", new java.math.BigInteger(1, digest));
    }

}
