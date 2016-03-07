/* global define: true, document: true */
define(['jquery', 'js/models/requests'],
        function ($, Requests) {
            "use strict";
            function getUserProjectsPath(userId) {
                return "/user/" + userId + "/projects";
            }
            function getProjectPath(projectId) {
                return "/project/" + projectId;
            }
            return {
                getUserProjectsPath: getUserProjectsPath,
                getUserProjects: function (id) {
                    return Requests.getJSON(getUserProjectsPath(id));
                },
                getProjectPath: getProjectPath,
                getProject: function (projectId) {
                    return Requests.getJSON(getProjectPath(projectId));
                }

            };
        });