/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;


import com.com3014.group1.projectmatching.model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author George
 */
@Service("detailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(userName);
        if(user != null){
              return new org.springframework.security.core.userdetails.User(userName, "", buildAuthUser(user));
        }else {
            throw new UsernameNotFoundException("User Not Found");
        }
      
    }
    private List<GrantedAuthority> buildAuthUser(User user){
         List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
          authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;

    }
 
    
}
