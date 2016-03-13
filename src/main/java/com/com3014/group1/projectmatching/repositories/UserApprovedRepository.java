/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.repositories;
import com.com3014.group1.projectmatching.model.Project;
import com.com3014.group1.projectmatching.model.User;
import com.com3014.group1.projectmatching.model.UserApproved;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface UserApprovedRepository extends JpaRepository<UserApproved, UserApproved.UserApprovedPK>{
    public List<Project> findByUser(User user);
}
