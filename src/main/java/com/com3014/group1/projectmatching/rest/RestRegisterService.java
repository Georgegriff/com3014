/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.UserService;
import com.com3014.group1.projectmatching.dto.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sam Waters
 * @author George Griffiths
 */
@RestController
public class RestRegisterService {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(method=RequestMethod.POST, value = "/register", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUser(JSONObject newUser) {
        try {
            User user = new User();
        user.setForename((String) newUser.get("firstName"));
        user.setSurname((String) newUser.get("lastName"));
        user.setEmail((String) newUser.get("email"));
        user.setUsername((String) newUser.get("username"));
         //user.setLastLogin((String) newUser.get("firstName"));
        if(!userService.registerUser(user)){
           return new ResponseEntity<String>(HttpStatus.BAD_REQUEST); 
        }
        return new ResponseEntity<String>(HttpStatus.OK);
        }catch(JSONException ex){
             return new ResponseEntity<String>(HttpStatus.BAD_REQUEST); 
        }
        
       
    }
    
}
