/* global define: true, document: true */
define(['jquery', 'js/models/requests'],
        function ($, Requests) {
            "use strict";
            var MATCHES = "/matches";
            function getProjectMatcherPath(projectId) {
                return MATCHES + "/project/" + projectId;
            }
            function getMatchesForRole(roleId) {
                return Requests.getJSON(MATCHES + "/role/" + roleId);
            }
            return {
                getProjectMatcherPath: getProjectMatcherPath,
                getMatchesForRole: getMatchesForRole
            };
        });