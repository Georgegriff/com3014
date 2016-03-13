/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.repositories;

import com.com3014.group1.projectmatching.model.Role;
import com.com3014.group1.projectmatching.model.RoleSkill;
import com.com3014.group1.projectmatching.model.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface RoleSkillRepository extends JpaRepository<RoleSkill, Integer> {
     public List<Skill> findByRole(Role role);
}
