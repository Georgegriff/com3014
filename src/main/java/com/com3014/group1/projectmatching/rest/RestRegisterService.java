package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.PasswordService;
import com.com3014.group1.projectmatching.core.services.UserService;
import com.com3014.group1.projectmatching.dto.RegisterUser;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatchmaking.helpers.GoogleGeoCode;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Service for Registering Users
 *
 * @author Sam Waters
 * @author George Griffiths
 */
@RestController
public class RestRegisterService {

    @Autowired
    private UserService userService;
       
    @Autowired
    private PasswordService passwordService;

    /**
     * Register a User
     *
     * @param registerUser The User to register
     * @return Whether the User was registered
     */
    @RequestMapping(method = RequestMethod.POST, value = "/register", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUser(@RequestBody RegisterUser registerUser) {
        User user = registerUser.getUser();
        user.setLastLogin(new Date());
        user.setName(user.getForename() + ' ' + user.getSurname());
        try {
            double[] latlon = GoogleGeoCode.getLatLngFromPostCode(user.getLocation().getStringLocation());
            if (latlon.length == 2) {
                user.getLocation().setLatitude(latlon[0]);
                user.getLocation().setLongitude(latlon[1]);
            }
        } catch (NumberFormatException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity;
        try {
            userEntity = userService.registerUser(user);
        } catch (Exception e) {
            userEntity = null;                    
        }
        
        if (userEntity == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // then save user password password
        this.passwordService.addPassword(userEntity, registerUser.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
