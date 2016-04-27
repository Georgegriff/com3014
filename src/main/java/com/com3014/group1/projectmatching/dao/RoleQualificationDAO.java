package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectRoleEntity;
import com.com3014.group1.projectmatching.model.RoleQualification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Qualifications relating to a Project Role
 *
 * @author Daniel
 */
public interface RoleQualificationDAO extends JpaRepository<RoleQualification, RoleQualification.RoleQualificationPK> {

    public List<RoleQualification> findByProjectRole(ProjectRoleEntity projectRole);
}
