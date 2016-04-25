/* global define: true, document: true */
define(['jquery','js/models/requests'],
        function ($, Requests) {
            "use strict";
            var USER_PATH = "/user",
                currentUser = null;
            return {
                getUser: function() {
                    return Requests.getJSON(USER_PATH).then(function (user) {
                        currentUser = user;
                        return user;
                    });                    
                },
                
               getProfile: function() {
                   return "/user";
               },
                getProjects: function () {
                    return "/user/projects";
                }
            };
        });