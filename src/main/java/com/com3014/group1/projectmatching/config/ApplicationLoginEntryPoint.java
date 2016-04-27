package com.com3014.group1.projectmatching.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * The entry point for the login request
 *
 * @author George
 */
class ApplicationLoginEntryPoint extends LoginUrlAuthenticationEntryPoint {

    /**
     * Constructor to call constructor of super class
     *
     * @param loginFormUrl The URL from the login form
     */
    public ApplicationLoginEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    /**
     *
     * @param request The HTTP Servlet Request made by the front end
     * @param response The HTTP Servlet Response
     * @param authException An Authentication Exception, set to null if no
     * exception has been thrown
     * @throws IOException Exception thrown both by the super class version of
     * this method and in sending the error with the response object
     * @throws ServletException Exception thrown by the super class version of
     * this method
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.sendError(403, "Forbidden");
        } else {
            super.commence(request, response, authException);
        }
    }

}
