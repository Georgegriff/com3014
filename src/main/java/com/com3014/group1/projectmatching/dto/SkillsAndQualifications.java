package com.com3014.group1.projectmatching.dto;

import com.com3014.group1.projectmatching.model.QualificationLevel;
import com.com3014.group1.projectmatching.model.Skill;
import java.util.List;

/**
 *
 * @author George
 */
public class SkillsAndQualifications {
    
    private List<Skill> skills;
    
    private List<QualificationLevel> qualifications;
    
    public SkillsAndQualifications(){
        
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<QualificationLevel> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<QualificationLevel> qualifications) {
        this.qualifications = qualifications;
    }
    
    
    
}
