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
                // Clear the arrays
                var accepted = JSON.stringify(usersAccepted);
                var rejected = JSON.stringify(usersRejected);
                clearSwipedUsers();
                console.log("GETTING HERE");
                return Requests.getJSON(MATCHES + "/project/" + projectId + "/save/" + accepted + "/" + rejected);
            }
            
            function saveSwipedProjects(userId) {
                // Clear the arrays
                var accepted = JSON.stringify(projectsAccepted);
                var rejected = JSON.stringify(projectsRejected);
                clearSwipedProjects();
                console.log("GETTING HERE");
                return Requests.getJSON(MATCHES + "/user/" + userId + "/save/" + accepted + "/" + rejected);
            }
            
            function clearSwipedUsers() {
                usersRejected = [];
                usersAccepted = [];
            }
            
            function clearSwipedProjects() {
                projectsRejected = [];
                projectsAccepted = [];
            }
            
            function addToUsersAccepted(userId) {
                return usersAccepted.push(userId);
            }
            
            function addToUsersRejected(userId) {
                return usersRejected.push(userId);
            }
            
            function addToProjectsAccepted(projectId) {
                return projectsAccepted.push(projectId);
            }
            
            function addToProjectsRejected(projectId) {
                return projectsRejected.push(projectId);
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