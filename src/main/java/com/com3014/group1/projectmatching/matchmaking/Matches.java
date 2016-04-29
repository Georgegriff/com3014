package com.com3014.group1.projectmatching.matchmaking;

import com.com3014.group1.projectmatching.core.services.UserMatchService;
import com.com3014.group1.projectmatching.core.services.ProjectMatchService;
import com.com3014.group1.projectmatching.core.services.ProjectRoleService;
import com.com3014.group1.projectmatching.core.services.ProjectService;
import com.com3014.group1.projectmatching.core.services.UserService;
import com.com3014.group1.projectmatching.dto.MatchedUser;
import com.com3014.group1.projectmatching.dto.Project;
import com.com3014.group1.projectmatching.dto.User;
import com.com3014.group1.projectmatching.model.ProjectApproved;
import org.springframework.beans.factory.annotation.Autowired;
import com.com3014.group1.projectmatching.model.ProjectEntity;
import com.com3014.group1.projectmatching.model.RoleEntity;
import com.com3014.group1.projectmatching.model.UserApproved;
import com.com3014.group1.projectmatching.model.UserEntity;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elliot
 * @author George
 */
@Service
public class Matches {

    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private ProjectMatchService projectMatchService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRoleService projectRoleService;

    @Autowired
    private UserService userService;

    /**
     * Find the mutual matches for a Project, the Users that have approved the
     * Project that the Project manager has also approved
     *
     * @param projectId The ID of the Project
     * @return The mutual matches
     */
    public List<MatchedUser> findMutualMatchesForProject(int projectId) {
        ProjectEntity project = projectService.findProjectById(projectId);
        List<MatchedUser> mutualMatchedUsers = new ArrayList<>();
        List<ProjectApproved> usersMatches = userMatchService.getUsersMatchedToProject(project);
        for (ProjectApproved userMatched : usersMatches) {
            UserEntity user = userMatched.getUser();
            List<UserApproved> userProjectsMatched = projectMatchService.getProjectsMatchedToUser(user);
            for (UserApproved approvedProject : userProjectsMatched) {
                if ((approvedProject.getProject().equals(project))) {
                    try {

                        mutualMatchedUsers.add(convertToMatchedUser(userMatched));
                    } catch (ObjectNotFoundException ex) {

                    }
                }
            }
        }
        return mutualMatchedUsers;
    }

    /**
     * Check if a user is contained within a list of User matches
     *
     * @param user The user
     * @param matches The matches
     * @return Whether the User is contained within the list of matches
     */
    private boolean searchForMatchedUser(UserApproved user, List<UserApproved> matches) {
        return matches.contains(user);
    }

    /**
     * Check if a project is contained within a list of project matches
     *
     * @param project The Project
     * @param matches The matches
     * @return Whether the project is contained within the list of matches
     */
    private boolean searchForMatchedProject(ProjectApproved project, List<ProjectApproved> matches) {
        return matches.contains(project);
    }

    /**
     * Convert a Project Approval into a matched User
     *
     * @param projectApproval The project approval
     * @return The matched User
     */
    private MatchedUser convertToMatchedUser(ProjectApproved projectApproval) {
        User user = userService.convertEntityToUser(projectApproval.getUser());
        Project project = projectService.convertEntityToProject(projectApproval.getProject());
        RoleEntity role = projectApproval.getRole();
        return new MatchedUser(user, project, role);
    }

}
