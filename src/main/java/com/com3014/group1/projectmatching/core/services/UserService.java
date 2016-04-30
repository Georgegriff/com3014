package com.com3014.group1.projectmatching.core.services;

import com.com3014.group1.projectmatching.dao.QualificationLevelDAO;
import com.com3014.group1.projectmatching.dao.SkillDAO;
import com.com3014.group1.projectmatching.dao.UserDAO;
import com.com3014.group1.projectmatching.dao.UserInterestDAO;
import com.com3014.group1.projectmatching.dao.UserQualificationDAO;
import com.com3014.group1.projectmatching.dao.UserSkillDAO;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.model.QualificationLevel;
import com.com3014.group1.projectmatching.model.Skill;
import com.com3014.group1.projectmatching.model.UserEntity;
import com.com3014.group1.projectmatching.model.UserInterest;
import com.com3014.group1.projectmatching.model.UserQualification;
import com.com3014.group1.projectmatching.model.UserSkill;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A service to provide User retrieval and saving to the REST Service
 *
 * @author Sam Waters
 * @author Dan Ashworth
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserSkillDAO userSkillDAO;

    @Autowired
    private UserQualificationDAO userQualDAO;

    @Autowired
    private UserInterestDAO userInterestDAO;

    @Autowired
    private ProjectMatchService projectMatchService;
    
    @Autowired
    private SkillDAO skillDAO;
    
    @Autowired
    private QualificationLevelDAO qualificationLevelDAO;

    /**
     * Get the User from the ID
     *
     * @param id The ID of the User
     * @return The User
     */
    public User getUser(int id) {
        User user;

        try {
            // Get the user entity from the db
            UserEntity userEntity = userDAO.findOne(id);
            // Convert to actual user object
            user = convertEntityToUser(userEntity);
        } catch (ObjectNotFoundException onf) {
            user = null;
            onf.printStackTrace();
        }
        return user;
    }

    /**
     * Get all Users within the system
     *
     * @return All the Users within the system
     */
    public Map<Integer, User> getAllUsers() {
        Map<Integer, User> usersMap = new HashMap<>();
        try {
            // Find all the users
            List<UserEntity> allUsers = userDAO.findAll();
            for (int i = 0; i < allUsers.size(); i++) {
                User user = convertEntityToUser(allUsers.get(i));
                usersMap.put(user.getUserId(), user);
            }
        } catch (ObjectNotFoundException onf) {
            usersMap = null;
        }
        return usersMap;
    }

    /**
     * Get the list of users that have already been swiped by the project
     * manager
     *
     * @param projectId The ID of the project
     * @return The list of already swiped Users
     */
    public Map<Integer, User> getAlreadySwipedUsers(int projectId) {
        HashMap<Integer, User> alreadySwiped = new HashMap<>();
        try {
            List<UserEntity> entities = this.projectMatchService.getAlreadySwipedUsers(projectId);

            for (UserEntity user : entities) {
                alreadySwiped.put(user.getUserId(), convertEntityToUser(user));
            }
        } catch (ObjectNotFoundException onf) {
            alreadySwiped = null;
        }
        return alreadySwiped;
    }

    /**
     * Get a User from their Username
     *
     * @param userName The Username
     * @return The User
     */
    public User getUserByUsername(String userName) {
        User user;

        try {
            UserEntity userEntity = userDAO.findByUsername(userName);
            user = convertEntityToUser(userEntity);
        } catch (ObjectNotFoundException onf) {
            user = null;
        }

        return user;
    }

    /**
     * Register a User
     *
     * @param user The User to register
     * @return Whether the User was registered
     */
    @Transactional
    public UserEntity registerUser(User user) {
        if (!validUser(user)) {
            return null;
        }
        
        // Save the top level user entity
        UserEntity userEntity = convertUserToEntity(user);
        userDAO.save(userEntity);
        
        // Save user interest
        for (UserInterest interest : user.getInterestsList()) {
            interest.setUser(userEntity);
        }
        userInterestDAO.save(user.getInterestsList());
        // Save user skill
        for (UserSkill skill : user.getSkillsList()) {
            skill.setUser(userEntity);
            // Get the persisted entity from the db.
            Skill skillEntity = this.skillDAO.findOne(skill.getSkill().getSkillId());
            skill.setSkill(skillEntity);
        }
        userSkillDAO.save(user.getSkillsList());
        // Save user qualifications
        for (UserQualification qual : user.getQualificationsList()) {
            qual.setUser(userEntity);
            QualificationLevel qualLevel = this.qualificationLevelDAO.findOne(qual.getQualificationLevel().getQualificationId());
            qual.setQualificationLevel(qualLevel);
        }
        userQualDAO.save(user.getQualificationsList());
        
        return userEntity;
    }

    /**
     * Check whether the given User is valid
     *
     * @param user The User to check
     * @return Whether the User was valid
     */
    private boolean validUser(User user) {
        if (user.getForename().equals("") || user.getForename().isEmpty() || user.getForename() == null) {
            return false;
        }
        if (user.getName().equals("") || user.getName().isEmpty() || user.getName() == null) {
            return false;
        }
        if (user.getUsername().equals("") || user.getUsername().isEmpty() || user.getUsername() == null) {
            return false;
        }
        if (user.getEmail().equals("") || user.getEmail().isEmpty() || user.getEmail() == null || !isValidEmailAddress(user.getEmail())) {
            return false;
        }
        return true;
    }

    /**
     * Check whether the given email address is valid
     *
     * @param email The email address to check
     * @return Whether the email address is valid
     */
    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Convert a UserEntity a User
     *
     * @param entity The UserEntity to convert
     * @return The User
     * @throws ObjectNotFoundException Exception thrown if the given entity is
     * null
     */
    public User convertEntityToUser(UserEntity entity) throws ObjectNotFoundException {

        if (entity != null) {
            // Get the attributes of the user
            List<UserSkill> userSkills = userSkillDAO.findByUser(entity);
            List<UserQualification> userQualifications = userQualDAO.findByUser(entity);
            List<UserInterest> userInterets = userInterestDAO.findByUser(entity);
            // Convert the DAO UserEntity to the DTO User object
            return new User(entity, userSkills, userQualifications, userInterets);
        } else {
            throw new ObjectNotFoundException(entity, "User Not Found");
        }
    }

    /**
     * Convert a User to a User Entity
     *
     * @param user The User
     * @return The User Entity
     */
    public UserEntity convertUserToEntity(User user) {
        // See if the user already exists in the datbase, get persisted
        UserEntity entity = null;
        if (user.getUserId() != null) {
            entity = userDAO.findOne(user.getUserId());
        }

        // If not create a new user object
        if (entity == null) {
            entity = new UserEntity();
        }

        entity.setUsername(user.getUsername());
        entity.setName(user.getForename());
        entity.setSurname(user.getSurname());
        entity.setEmail(user.getEmail());
        entity.setAverageRating(user.getAverageRating());
        entity.setLastLogin(user.getLastLogin());
        entity.setLocation(user.getLocation());

        return entity;
    }

    /**
     * Convert a list of User Entities to a list of Users
     *
     * @param entities The list of User Entities
     * @return The list of USers
     */
    public List<User> convertEntityListToUserList(List<UserEntity> entities) {
        List<User> users = new ArrayList<>();
        for (UserEntity entity : entities) {
            try {
                User user = convertEntityToUser(entity);
                users.add(user);
            } catch (ObjectNotFoundException onf) {
                users = null;
                break;
            }
        }
        return users;
    }

    /**
     * Find a User by it's ID
     *
     * @param id The ID of the User
     * @return The User
     */
    public UserEntity findEntityById(int id) {
        return this.userDAO.findOne(id);
    }
}
