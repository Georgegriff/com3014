/*
 * Controller for Static Paths in the Application
 */
package com.com3014.group1.projectmatching.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author George
 */
@Configuration
public class RouteViewController extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/user/{id}").setViewName("index");
        registry.addViewController("/project/{id}").setViewName("index");
        registry.addViewController("user/{id}/projects").setViewName("index");
        registry.addViewController("/projectmatches/{projectId}").setViewName("index");
        registry.addViewController("matches/project/{projectId}").setViewName("index");

    }
}
