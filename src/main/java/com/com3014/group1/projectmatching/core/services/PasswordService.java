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
import org.springframework.transaction.annotation.Transactional;

/**
 * A service to provide password functionality to the REST Service
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

    /**
     * Check that the given password is correct
     *
     * @param username The username to check the password for
     * @param submittedPassword The password that the user submitted
     * @return Whether the password was correct
     */
    public boolean checkPassword(String username, String submittedPassword) {
        // Null check
        if (username == null || submittedPassword == null) {
            return false;
        }

        UserEntity userEntity = userDAO.findByUsername(username);

        if (userEntity != null) {
            try {
                // Find the user's actual password
                System.out.println("ID: " + userEntity.getUserId());
                Password userPassword = passwordDAO.findByUserId(userEntity.getUserId());
                // Hash the submitted password
                String submittedPasswordHash = hashPassword(submittedPassword);
                return userPassword != null && submittedPasswordHash.equals(userPassword.getPassword());
            } // Catch errors from the hashPassword method
            catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                Logger.getLogger(PasswordService.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }

    /**
     * Change the user's password
     *
     * @param username The username of the user that wishes to change their
     * password
     * @param oldPassword The user's old password
     * @param newPassword The desired new password
     * @throws AuthenticationException Exception thrown if the user fails to
     * authenticate
     * @throws IllegalArgumentException Exception thrown if the new password is
     * invalid
     */
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

    /**
     * Add a password for a user
     *
     * @param entity The user entity to add the password to
     * @param password The desired password
     * @throws IllegalArgumentException Exception thrown if the new password is
     * invalid
     */
    @Transactional
    public void addPassword(UserEntity entity, String password) throws IllegalArgumentException {
        if (!validPassword(password)) {
            // Maybe change to a different type of exception
            throw new IllegalArgumentException("New password is invalid") {
            };
        }
        
        try {
            Password userPassword = new Password();
            userPassword.setUserId(entity.getUserId());
            userPassword.setPassword(hashPassword(password));
            passwordDAO.save(userPassword);
            
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(PasswordService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Check if the password is valid
     *
     * @param password The desired password
     * @return Whether the desired password is valid
     */
    private boolean validPassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }
        if (password.matches(".*[0-9].*") && (password.matches(".*[A-Z].*") || password.matches(".*[a-z].*"))) {
            return true;
        }
        return false;
    }

    /**
     * Hash the password
     *
     * @param password The password to hash
     * @return The hashed password
     * @throws NoSuchAlgorithmException Exception thrown if the hashing
     * algorithm is not available
     * @throws UnsupportedEncodingException Exception thrown if the encoding
     * algorithm is not available
     */
    private String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        byte[] digest = md.digest();
        return String.format("%064x", new java.math.BigInteger(1, digest));
    }

}
