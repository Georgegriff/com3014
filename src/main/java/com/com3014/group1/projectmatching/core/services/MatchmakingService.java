/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.ProjectDAO;
import com.com3014.group1.projectmatching.dao.RoleDAO;
import com.com3014.group1.projectmatching.dto.Role;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.matchmaking.Matchmaking;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ggriffiths
 */
@Service
public class MatchmakingService {

    @Autowired
    private ProjectRoleService roleService;

    @Autowired
    private Matchmaking matching;

    /**
     *
     * @param roleId
     * @return
     */
    public List<User> matchUserToRole(int roleId) {
        Role role = roleService.findRoleById(roleId);
        if (role != null) {
            return matching.findUsersForRole(role);
        }else {
            return new ArrayList<User>();
        }
    }
}
