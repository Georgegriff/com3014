package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectRoleEntity;
import com.com3014.group1.projectmatching.model.RoleSkill;
import com.com3014.group1.projectmatching.model.RoleSkill.RoleSkillPK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Skills relating to a Project Role
 *
 * @author Daniel
 */
public interface RoleSkillDAO extends JpaRepository<RoleSkill, RoleSkillPK> {

    public List<RoleSkill> findByProjectRole(ProjectRoleEntity projectRole);
}
