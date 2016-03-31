/*
 * To change this license header, choose License Headers in ProjectEntity Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.UserMatch;
import com.com3014.group1.projectmatching.model.UserSet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel
 */
@Transactional(readOnly = true)
public interface UserSetDAO extends JpaRepository<UserSet, UserSet.UserSetPK> {
    
    public List<UserSet> findBySet(UserMatch set);
    
    @Modifying
    @Transactional
    public void deleteBySet(UserMatch set);
}
