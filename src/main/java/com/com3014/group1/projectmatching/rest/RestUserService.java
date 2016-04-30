package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.SkillQualificationService;
import com.com3014.group1.projectmatching.dto.SkillsAndQualifications;
import com.com3014.group1.projectmatching.dto.User;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Service for Users
 *
 * @author George
 */
@RestController
@RequestMapping("/services")
public class RestUserService {

    @Autowired
    private SkillQualificationService skillQualService;

    /**
     * Get the current user from the session
     *
     * @param session The session
     * @return The current user
     */
    @RequestMapping(value = "/user", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUser(HttpSession session) {
        User user =  (User) session.getAttribute("currentUser");
        if (user != null) {
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Could Not Retrieve User", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get the lists of Skills and Qualifications
     *
     * @return The list of Skills and Qualifications
     */
    @RequestMapping(value = "/userskills", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public SkillsAndQualifications getSkillsAndQualificationsLists() {
        SkillsAndQualifications skilsqual = new SkillsAndQualifications();
        skilsqual.setQualifications(skillQualService.getAllQuals());
        skilsqual.setSkills(skillQualService.getAllSkills());
        return skilsqual;
    }
}
