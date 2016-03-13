/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.repositories;

import com.com3014.group1.projectmatching.model.Skill;
import com.com3014.group1.projectmatching.model.User;
import com.com3014.group1.projectmatching.model.UserSkill;
import com.com3014.group1.projectmatching.model.UserSkill.UserSkillPK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface UserSkillRepository extends JpaRepository<UserSkill, UserSkillPK>{
    
    public List<Skill> findByUser(User user);
    
}
