/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/pages/project-matches/template/template.htm'],
       function (_, $, Template) {
           "use strict";
           return {
               init: function (app, pageName) {
                    function Page() {
                       var $page = null,
                               project;

                       function getProject() {
                           var path = window.location.pathname,
                                   splitPath = path.split("/"),
                                   id = splitPath[splitPath.length - 1];
                           var urlParams = {roleInfo: "true", interestInfo: "true"};
                           return app.models.project.getProject(id, urlParams);
                       }
                       function getProjectId(data) {
                           if (data) {
                               project = data;
                           }
                           return data.projectId;
                       }
                       function render() {
                           getProject().then(getProjectId)
                                   .then(app.models.matches.getProjectMatches)
                                   .then(function (data) {
                                       if (data) {
                                           console.log(data);
                                 
                                           $page = $(app.parseTemplate(Template, {
                                               projectsLink: app.models.project.getUserProjectsPath(app.currentUser.userId),
                                                matches: data || [],
                                               name: "Matches"
                                           }));
                                           app.container.showContent($page);
                                       }
                                   });
                       }
                       function show() {
                            render();
                       }
                       return {
                           show: show
                       };
                   }
                   app.pages[pageName] = new Page();
               }
           };
       });