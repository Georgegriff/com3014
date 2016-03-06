/*
 * Manages databse interactions for Projects
 */
package com.com3014.group1.projectmatching.dao;

import com.com3014.group1.projectmatching.core.enums.PaymentEnum;
import com.com3014.group1.projectmatching.model.Location;
import com.com3014.group1.projectmatching.model.Payment;
import com.com3014.group1.projectmatching.model.Project;
import com.com3014.group1.projectmatching.model.Qualification;
import com.com3014.group1.projectmatching.model.Role;
import com.com3014.group1.projectmatching.model.Skill;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sam Waters
 */
@Repository
public class ProjectDAO {
    
    final private Map<Integer, Project> projects;
    
    public ProjectDAO(){
        // temp until database
        projects = getAllProjects();
    }
    
    public Project findProjectById(int id){
       return projects.get(id);
    }
    
    private Map<Integer, Project> getAllProjects() {

        List<Skill> skills1 = new ArrayList<Skill>();
        skills1.add(new Skill("Java", 12));
        skills1.add(new Skill("Android Development", 12));

        List<Qualification> qualifications1 = new ArrayList<Qualification>();
        qualifications1.add(new Qualification("Computer Sceince", "Degree"));
        qualifications1.add(new Qualification("Maths", "A Level"));

        Role role1 = new Role(1, "Developer1", new Payment(PaymentEnum.TBC, 0), skills1, qualifications1);
        Role role2 = new Role(2, "Developer2", new Payment(PaymentEnum.TBC, 0), skills1, qualifications1);

        List<Role> roles1 = new ArrayList<Role>();
        roles1.add(role1);
        roles1.add(role2);

        Project project1 = new Project();
        project1.setProjectId(1);
        project1.setName("Mobile Game");
        project1.setDescription("Insert generic Mobile Game development description");
        project1.setProjectStart(new Date(2016, 3, 20));
        project1.setEstimatedEnd(new Date(2016, 5, 20));
        project1.setLocation(new Location(51.50, -0.12));
        project1.setRoles(roles1);
        project1.setInterests(new ArrayList<String>(Arrays.asList(new String[]{"Game Design", "Mobile"})));

        List<Skill> skills2 = new ArrayList<Skill>();
        skills2.add(new Skill("AngularJS", 24));
        skills2.add(new Skill("Web Development", 36));

        List<Qualification> qualifications2 = new ArrayList<Qualification>();
        qualifications1.add(new Qualification("Computer Sceince", "Degree"));

        Role role3 = new Role(1, "Developer1", new Payment(PaymentEnum.HOURLY, 20), skills2, qualifications2);
        Role role4 = new Role(2, "Developer2", new Payment(PaymentEnum.HOURLY, 20), skills2, qualifications2);

        List<Role> roles2 = new ArrayList<Role>();
        roles1.add(role3);
        roles1.add(role4);

        Project project2 = new Project();
        project2.setProjectId(2);
        project2.setName("Tennis Website");
        project2.setDescription("Insert generic Web development description");
        project2.setProjectStart(new Date(2016, 3, 30));
        project2.setEstimatedEnd(new Date(2016, 9, 300));
        project2.setLocation(new Location(51.50, -0.12));
        project2.setRoles(roles2);
        project2.setInterests(new ArrayList<String>(Arrays.asList(new String[]{"Web design", "Tennis"})));

        Map<Integer, Project> projects = new HashMap<Integer,Project>();
        projects.put(project1.getProjectId(), project1);
        projects.put(project2.getProjectId(), project2);

        return projects;
    }

}
