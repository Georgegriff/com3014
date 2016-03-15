/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

@Component()
public class AuthProvider extends AbstractUserDetailsAuthenticationProvider  {
    
    
    @Autowired
    @Qualifier("detailsService")
    private UserDetailsService detailsService;
    
    @Autowired
    private PasswordService passwordService;
    
    @Override
    protected void additionalAuthenticationChecks(UserDetails ud, UsernamePasswordAuthenticationToken upat) throws AuthenticationException {
       // other checks
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken upat) throws AuthenticationException {
          boolean thirdPartyAuthentication = false;   
        if (username !=null && upat != null && passwordService.checkPassword(username, (String) upat.getCredentials())) {
            try {
                 return detailsService.loadUserByUsername(username);     
            }catch(UsernameNotFoundException e) {
                  throw new AuthenticationException("Unable to authenticate") {};
            }
        }else {
              throw new AuthenticationException("Unable to authenticate") {};
        }
    }

  

}
