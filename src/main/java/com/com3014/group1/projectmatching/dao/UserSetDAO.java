package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.model.UserMatch;
import com.com3014.group1.projectmatching.model.UserSet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface used to define database call needed by Hibernate to access the
 * Users from a UserMatch
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
