/* global define: true, document: true */
define(['jquery'],
        function ($) {
            "use strict";
            var USER_PATH = "/user",
                    CURRENT_USER = "/userinfo",
                    currentUser = null;
            return {
                getUser: function (id) {
                    return $.getJSON(USER_PATH + id);
                },
                getCurrentUser: function () {
                    return $.getJSON(CURRENT_USER).then(function (user) {
                        currentUser = user;
                        return user;
                    });
                },
                getProfile: function (id) {
                    if (!id) {
                        id = currentUser.userId;
                    }
                    return "/user/" + id;
                },
                getProjects: function (userId) {
                    if (!userId) {
                        userId = currentUser.userId;
                    }
                    return "/user/" + userId + "/projects";
                }
            };
        });