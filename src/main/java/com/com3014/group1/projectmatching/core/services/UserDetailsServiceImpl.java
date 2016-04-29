package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dto.User;
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
 * A service to provide UserDetails
 *
 * @author George
 */
@Service("detailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * Retrieve the details of a user from their username
     *
     * @param userName The username of the user you wish to retrieve
     * @return The User Details
     * @throws UsernameNotFoundException Exception thrown if the user is not
     * found
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(userName);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(userName, "", buildAuthUser(user));
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }

    }

    /**
     *
     * @param user
     * @return
     */
    private List<GrantedAuthority> buildAuthUser(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;

    }

}
