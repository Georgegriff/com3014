/* global define: true, document: true */
define(['jquery', 'js/models/requests'],
        function ($, Requests) {
            "use strict";
            
            var MATCHES = "/matches";
            var usersAccepted = [];
            var usersRejected = [];
            var projectsAccepted = [];
            var projectsRejected = [];
            var projectIds = [];
            
            function getProjectMatcherPath(projectId) {
                return MATCHES + "/project/" + projectId;
            } 
            
            function getProjectMatchesPath(projectId) {
               return "/projectmatches/" + projectId;
           }
           
           function getProjectMatches(id){
               return Requests.getJSON(MATCHES + "/project/" +  + id + "/matches");
           }
            
            function getMatchesForRole(roleId, projectId) {
                return Requests.getJSON(MATCHES + "/project/" + projectId + "/role/" + roleId);
            }
            
            function getMatchesForUser() {
                return Requests.getJSON(MATCHES + "/user/roles");
            }
            
            function saveSwipedUsers(projectId) {
                var acceptedJSON = {accepted : usersAccepted};
                var rejectedJSON = {rejected : usersRejected};
                // Clear arrays
                clearSwipedUsers();
                return Requests.postPreferences(MATCHES + "/project/" + projectId + "/save", acceptedJSON, rejectedJSON);
            }
            
            function saveSwipedProjects() {
                var acceptedJSON = {accepted : projectsAccepted};
                var rejectedJSON = {rejected : projectsRejected};
                // Clear the arrays
                clearSwipedProjects();
                return Requests.postPreferences(MATCHES + "/user/save", acceptedJSON, rejectedJSON);
            }
            
            function clearSwipedUsers() {
                usersRejected = [];
                usersAccepted = [];
            }
            
            function clearSwipedProjects() {
                projectsRejected = [];
                projectsAccepted = [];
            }
            
            function addToUsersAccepted(userRole) {
                return usersAccepted.push(userRole);
            }
            
            function addToUsersRejected(userRole) {
                return usersRejected.push(userRole);
            }
            
            function addToProjectsAccepted(projectRole) {
                return projectsAccepted.push(projectRole);
            }
            
            function addToProjectsRejected(projectRole) {
                return projectsRejected.push(projectRole);
            }
            
            function addProjectId(id) {
                projectIds.push(id);
            }
            
            function getProjectIds() {
                return projectIds;
            }
            
            return {
                getProjectMatcherPath: getProjectMatcherPath,
                getProjectMatchesPath : getProjectMatchesPath,
                getProjectMatches : getProjectMatches,
                getMatchesForRole: getMatchesForRole,
                getMatchesForUser : getMatchesForUser,
                saveSwipedUsers: saveSwipedUsers,
                saveSwipedProjects: saveSwipedProjects,
                clearSwipedUsers: clearSwipedUsers,
                clearSwipedProjects: clearSwipedProjects,
                addToUsersAccepted: addToUsersAccepted,
                addToUsersRejected: addToUsersRejected,
                addToProjectsAccepted: addToProjectsAccepted,
                addToProjectsRejected: addToProjectsRejected,
                addProjectId: addProjectId,
                getProjectIds: getProjectIds
            };
        });