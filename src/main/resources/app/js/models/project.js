/* global define: true, document: true */
define(['jquery', 'js/models/requests'],
        function ($, Requests) {
            "use strict";
            function getUserProjectsPath(userId) {
                return "/user/" + userId + "/projects";
            }
            function getProjectPath(projectId, urlParams) {
                var request = "/project/" + projectId;
                var qryParams = "";
                
                for(var key in urlParams) {
                    if(qryParams === "") {
                        qryParams += "?";
                    }
                    else {
                        qryParams += "&";
                    }
                    qryParams += key + "=" + encodeURIComponent(urlParams[key]);
                }
                return request + qryParams; 
            }
            return {
                getUserProjectsPath: getUserProjectsPath,
                getUserProjects: function (id) {
                    return Requests.getJSON(getUserProjectsPath(id));
                },
                getProjectPath: getProjectPath,
                getProject: function (projectId, urlParams) {
                    return Requests.getJSON(getProjectPath(projectId, urlParams));
                }
            };
        });