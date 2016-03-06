/* global define: true, document: true */
define(['jquery'],
        function ($) {
            "use strict";
            return {
                getUserProjectsPath: function (userId) {
                    return "/user/" + userId + "/projects";
                },
                getUserProjects: function (id) {
                    return $.getJSON("/user/" + id + "/projects");
                }
            };
        });