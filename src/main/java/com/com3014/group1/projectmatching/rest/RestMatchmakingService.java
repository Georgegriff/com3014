/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.MatchmakingService;
import com.com3014.group1.projectmatching.dto.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ggriffiths
 */
@RestController
@RequestMapping("/services/matches")
public class RestMatchmakingService {

    @Autowired
    private MatchmakingService matchmakingService;
    
    @RequestMapping(value = "/role/{roleId}", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public List<User> matchUserToRole(@PathVariable String roleId) {
        return matchmakingService.matchUserToRole(Integer.parseInt(roleId));
    }

}