package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserSkill;
import com.com3014.group1.projectmatching.model.UserSkill.UserSkillPK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Skills relating to a User
 *
 * @author Daniel
 */
public interface UserSkillDAO extends JpaRepository<UserSkill, UserSkillPK> {

    public List<UserSkill> findByUser(UserEntity user);

}
