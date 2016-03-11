
package com.com3014.group1.projectmatching.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author George
 */
@Controller
public class LoginViewController {
     @RequestMapping(value = "/login", headers ="Accept=" + MediaType.TEXT_HTML_VALUE)
    String loginPage() {
        return "login";
    }
}
