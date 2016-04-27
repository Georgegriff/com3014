package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectInterest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Interests of a Project
 *
 * @author Daniel
 */
public interface ProjectInterestDAO extends JpaRepository<ProjectInterest, Integer> {

    public List<ProjectInterest> findByProject(ProjectEntity project);
}
