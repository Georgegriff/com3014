package com.com3014.group1.projectmatching.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import com.com3014.group1.projectmatching.core.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

/**
 * Deals with successful authentication attempts
 */
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * Service used to pull user details from the database
     */
    @Autowired
    private UserService userService;

    /**
     * Method to run when the authentication of a user is successful
     *
     * @param request The HTTP Servlet Request made by the front end
     * @param response The HTTP Servlet Response
     * @param auth An authentication object containing required details of the
     * User
     * @throws IOException Exception thrown when the redirect fails
     * @throws ServletException Exception thrown by the super class version of
     * this method
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        HttpSession session = request.getSession();
        // Set the current user of the system to be the user authenticated in this call
        session.setAttribute("currentUser", userService.getUserByUsername(auth.getName()));
        DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
        if (defaultSavedRequest != null) {
            String requestUrl = defaultSavedRequest.getRequestURL() + "?" + defaultSavedRequest.getQueryString();
            getRedirectStrategy().sendRedirect(request, response, requestUrl);
        } else {
            super.onAuthenticationSuccess(request, response, auth);
        }
    }

}
