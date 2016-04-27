package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectMatch;
import com.com3014.group1.projectmatching.model.ProjectSet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Projects from a Project Match
 *
 * @author Daniel
 */
@Transactional(readOnly = true)
public interface ProjectSetDAO extends JpaRepository<ProjectSet, ProjectSet.ProjectSetPK> {

    public List<ProjectSet> findBySet(ProjectMatch set);

    @Modifying
    @Transactional
    public void deleteBySet(ProjectMatch set);

}
