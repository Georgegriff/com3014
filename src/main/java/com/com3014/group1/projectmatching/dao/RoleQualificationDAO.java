/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.RoleQualification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface RoleQualificationDAO extends JpaRepository<RoleQualification, RoleQualification.RoleQualificationPK>{

    public List<RoleQualification> findByRole(RoleEntity role);
}
