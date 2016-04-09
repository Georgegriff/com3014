/* global define: true, document: true */
define(['jquery', 'js/models/requests'],
        function ($, Requests) {
            "use strict";
            
            var MATCHES = "/matches";
            var usersAccepted = [];
            var usersRejected = [];
            var projectsAccepted = [];
            var projectsRejected = [];
            var projectId = null;
            
            function getProjectMatcherPath(projectId) {
                return MATCHES + "/project/" + projectId;
            }
            function getMatchesForRole(roleId, projectId) {
                return Requests.getJSON(MATCHES + "/project/" + projectId + "/role/" + roleId);
            }
            
            function getMatchesForUser() {
                return Requests.getJSON(MATCHES + "/user/roles");
            }
            
            function saveSwipedUsers(projectId) {
                var acceptedJSON = JSON.stringify({accepted : usersAccepted});
                var rejectedJSON = JSON.stringify({rejected : usersRejected});
                // Clear arrays
                clearSwipedUsers();
                return Requests.getJSON(MATCHES + "/project/" + projectId + "/save/" + acceptedJSON + "/" + rejectedJSON);
                //Stub below for PUT request change
                //return Requests.putJSON(MATCHES + "/project/" + projectId + "/save, acceptedJSON, rejectedJSON);
            }
            
            function saveSwipedProjects(userId) {
                var acceptedJSON = JSON.stringify({accepted : projectsAccepted});
                var rejectedJSON = JSON.stringify({rejected : projectsRejected});
                console.log(acceptedJSON);
                // Clear the arrays
                clearSwipedProjects();
                return Requests.getJSON(MATCHES + "/user/" + userId + "/save/" + acceptedJSON + "/" + rejectedJSON);
                //Stub below for put request change
                //return Requests.putJSON(MATCHES + "/user/" + userId + "/save", acceptedJSON, rejectedJSON);
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
                console.log("USER ACCEPTED");
                return usersAccepted.push(userRole);
            }
            
            function addToUsersRejected(userRole) {
                console.log("USER REJECTED");
                return usersRejected.push(userRole);
            }
            
            function addToProjectsAccepted(projectRole) {
                return projectsAccepted.push(projectRole);
            }
            
            function addToProjectsRejected(projectRole) {
                return projectsRejected.push(projectRole);
            }
            
            function setProjectId(id) {
                return (projectId = id);
            }
            
            function getProjectId() {
                return projectId;
            }
            
            return {
                getProjectMatcherPath: getProjectMatcherPath,
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
                setProjectId: setProjectId,
                getProjectId: getProjectId
            };
        });