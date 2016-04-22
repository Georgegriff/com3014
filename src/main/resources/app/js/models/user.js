/* global define: true, document: true */
define(['jquery','js/models/requests'],
        function ($, Requests) {
            "use strict";
            var USER_PATH = "/user",
                CURRENT_USER = "/userinfo",
                currentUser = null;
            return {
                getUser: function (id) {
                    return Requests.getJSON(USER_PATH + id);
                },
                getCurrentUser: function () {
                    return Requests.getJSON(CURRENT_USER).then(function (user) {
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