/* global define: true, document: true */

/**
 * 
 * JavaScript model for the Project Requests
 */
define(['jquery', 'js/models/requests'],
        function ($, Requests) {
            "use strict";
            function getUserProjectsPath() {
                return "/user/projects";
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
            
                getUserProjects: function () {
                    return Requests.getJSON(getUserProjectsPath());
                },
                getProjectPath: getProjectPath,
                getProject: function (projectId, urlParams) {
                    return Requests.getJSON(getProjectPath(projectId, urlParams));
                }
            };
        });