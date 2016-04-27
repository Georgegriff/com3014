package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.QualificationLevelDAO;
import com.com3014.group1.projectmatching.dao.SkillDAO;
import com.com3014.group1.projectmatching.dao.UserQualificationDAO;
import com.com3014.group1.projectmatching.model.QualificationLevel;
import com.com3014.group1.projectmatching.model.Skill;
import com.com3014.group1.projectmatching.model.UserQualification;
import com.com3014.group1.projectmatching.model.UserSkill;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author George
 */
@Service
public class SkillQualificationService {

    @Autowired
    private SkillDAO userSkillDAO;

    @Autowired
    private QualificationLevelDAO qualLevelDAO;

    public List<Skill> getAllSkills() {
        List<Skill> allSkills;
        try {
            // Find all the users
            allSkills = userSkillDAO.findAll();

        } catch (ObjectNotFoundException onf) {
            allSkills = null;
        }
        return allSkills;
    }

    public List<QualificationLevel> getAllQuals() {
        List<QualificationLevel> allQuals;
        try {
            // Find all the users
            allQuals = qualLevelDAO.findAll();

        } catch (ObjectNotFoundException onf) {
            allQuals = null;
        }
        return allQuals;
    }
}
