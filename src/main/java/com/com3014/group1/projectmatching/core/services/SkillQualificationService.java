package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.QualificationLevelDAO;
import com.com3014.group1.projectmatching.dao.SkillDAO;
import com.com3014.group1.projectmatching.model.QualificationLevel;
import com.com3014.group1.projectmatching.model.Skill;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service used to provide the lists of possible Skills and Qualifications
 *
 * @author George
 */
@Service
public class SkillQualificationService {

    @Autowired
    private SkillDAO userSkillDAO;

    @Autowired
    private QualificationLevelDAO qualLevelDAO;

    /**
     * Get the list of possible skills
     *
     * @return List of skills
     */
    public List<Skill> getAllSkills() {
        List<Skill> allSkills;
        try {
            // Find all the user skills
            allSkills = userSkillDAO.findAll();

        } catch (ObjectNotFoundException onf) {
            allSkills = null;
        }
        return allSkills;
    }

    /**
     * Get the list of possible qualifications
     *
     * @return List of qualifications
     */
    public List<QualificationLevel> getAllQuals() {
        List<QualificationLevel> allQuals;
        try {
            // Find all the user qualifications
            allQuals = qualLevelDAO.findAll();

        } catch (ObjectNotFoundException onf) {
            allQuals = null;
        }
        return allQuals;
    }
}
