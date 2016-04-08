/*
 * To change this license header, choose License Headers in ProjectEntity Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.ProjectApproved;
import com.com3014.group1.projectmatching.model.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface ProjectApprovedDAO extends JpaRepository<ProjectApproved, ProjectApproved.ProjectApprovedPK>{
    public List<UserEntity> findByProject(ProjectEntity project);
    public List<ProjectApproved> findByUser_UserId(int userId);
}
