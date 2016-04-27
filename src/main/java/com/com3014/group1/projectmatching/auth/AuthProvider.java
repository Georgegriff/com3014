package com.com3014.group1.projectmatching.auth;

import com.com3014.group1.projectmatching.core.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Provides authentication for Users
 */
@Component()
public class AuthProvider extends AbstractUserDetailsAuthenticationProvider {

    /**
     * Service used to pull user details from the database
     */
    @Autowired
    @Qualifier("detailsService")
    private UserDetailsService detailsService;

    /**
     * Service used to check the password of the user in the database
     */
    @Autowired
    private PasswordService passwordService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails ud, UsernamePasswordAuthenticationToken upat) throws AuthenticationException {
        // other checks
    }

    /**
     * Retrieve the details of the user given their credentials, if the parameters provided match with the database
     * 
     * @param username
     *      The Username given
     * @param upat
     *      The Username and Password Authentication token generated from the given password
     * @return
     *      The details of the given user
     * @throws AuthenticationException
     *      Authentication Exception thrown if either the username is not found or the password is incorrect
     */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken upat) throws AuthenticationException {
        if (username != null && upat != null && passwordService.checkPassword(username, (String) upat.getCredentials())) {
            try {
                return detailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                throw new AuthenticationException("Unable to authenticate") {
                };
            }
        } else {
            throw new AuthenticationException("Unable to authenticate") {
            };
        }
    }

}
