/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/pages/user-swiper/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app, pageName) {
                    function Page() {
                        var $page = $(app.parseTemplate(Template)),
                                $pending,
                                roleMatches = [],
                                project = {},
                                projectId = null,
                                roles = [],
                                swipes = 0;

                        // Triggers a save if the user navigates away from the application
                        $(window).bind('unload', function () {
                            app.models.matches.saveSwipedUsers(projectId);
                        });

                        function onDataLoad(swiper) {
                            if (roleMatches.length) {
                                app.plugins.swipers.show();
                                if (swiper) {
                                    //TODO:: check if roleMatches array is < threshold if so repopulate;
                                    var user = roleMatches[swiper.getPosition()],
                                            role = null;
                                    if (user) {
                                        role = user.matchedRole;
                                        swiper.init({
                                            onAccept: onAccept(user.userId, role.roleId),
                                            onReject: onReject(user.userId, role.roleId)
                                        });
                                        swiper.setImage("img/default-profile.svg");
                                        swiper.setTitle(user.name);
                                        swiper.setSubtitle(role.name);
                                        swiper.setRightSubtitle("Last Active " + lastActive(user.lastLogin) + " Days Ago");
                                        //swiper.addDescription("About");
                                        swiper.addList("Interests", user.interestsList, function (interest) {
                                            return interest.interest;
                                        });
                                        swiper.addList("Skills", user.skillsList, function (skill) {
                                            return skill.skill.name + ", " + skill.monthsOfExperience + " Months Experience.";
                                        });
                                        swiper.addList("Qualifications", user.qualificationsList, function (qualification) {
                                            return qualification.subject + ": " + qualification.qualificationLevel.qualificationLevel;
                                        });

                                    }
                                }
                            } else {
                                app.plugins.swipers.hide();
                            }
                        }
                        function lastActive(epoch) {
                            var currentTime = new Date(),
                                    time = new Date(epoch),
                                    difference = Math.abs(time.getTime() - currentTime.getTime()),
                                    days = Math.ceil(difference / (1000 * 3600 * 24));
                            return days;
                        }
                        function onAccept(userId, roleId) {
                            return function () {
                                // save to accepted array
                                var obj = new Object();
                                obj.user = userId;
                                obj.role = roleId;
                                app.models.matches.addToUsersAccepted(obj);

                                swipes++;
                                checkSwipeThreshold();
                                //Not sure why the below was added only here but it was causing strange
                                //matching behaviour so I have removed it
                                /*
                                 app.models.matches.addToUsersAccepted(userId)
                                 .then(function(returnData) {                  
                                 });
                                 */
                                // remove current entry from array
                                roleMatches.shift();
                            };
                        }
                        function onReject(userId, roleId) {
                            return function () {
                                // save to rejected array
                                var obj = new Object();
                                obj.user = userId;
                                obj.role = roleId;
                                app.models.matches.addToUsersRejected(obj);

                                swipes++;
                                checkSwipeThreshold();
                                // remove current entry from array
                                roleMatches.shift();
                            };
                        }
                        function checkSwipeThreshold() {
                            if (swipes > 5) {
                                app.models.matches.saveSwipedUsers(projectId);
                                swipes = 0;
                            }
                            return;
                        }
                        function getProject() {
                            var path = window.location.pathname,
                                    splitPath = path.split("/"),
                                    id = splitPath[splitPath.length - 1];
                            var urlParams = {roleInfo: "true", interestInfo: "true"};
                            return app.models.project.getProject(id, urlParams);
                        }
                        function prepareProjectData(data) {
                            project = data;
                            projectId = project.projectId;
                            app.models.matches.addProjectId(projectId);
                            roles = project.rolesList;
                            return roles;
                        }
                        function showLoading($pending) {
                            $('#content').append($pending);
                            $pending.fadeIn();
                        }
                        function getMatchesForRole(roleData) {
                            if (roleData.length > 0) {
                                var newRoles = [];
                                var firstRole = roleData[0].role;
                                return app.models.matches.getMatchesForRole(firstRole.roleId, projectId)
                                        .then(function (data) {
                                            if (roleData.length > 1) {
                                                roleData.splice(0,1);
                                                loadNewData(roleData, function (role, data) {
                                                    loadDone(role, data);
                                                });
                                            }
                                            return $.Deferred().resolve(firstRole, data);
                                        }).fail(function (err) {
                                    console.error(err);

                                });


                            }
                            return null;
                        }
                        function hidePending(action) {
                            $pending.fadeOut(500, function () {
                                $pending.remove();
                                if (_.isFunction(action)) {
                                    action();
                                }
                            });
                        }
                        function loadDone(role, data) {
                            var promise = $.Deferred(),
                                    index = 0;
                            if (_.isArray(data)) {
                                _.each(data, function (item) {
                                    if (item) {
                                        item.matchedRole = role;
                                    }
                                    index++;
                                    if (index >= data.length) {
                                        roleMatches = roleMatches.concat(data);
                                        return promise.resolve();
                                    }

                                });

                            } else {
                                return promise.reject();
                            }
                        }
                        function loadNewData(roles, pushData) {
                            if (roles.length > 0) {
                                roles.forEach(function (role) {
                                    var roleInfo = role.role;
                                    return app.models.matches.getMatchesForRole(roleInfo.roleId, projectId)
                                            .then(function (data) {
                                                return pushData(roleInfo, data);
                                            }).fail(function (err) {
                                        console.error(err);
                                    });
                                });
                            }
                        }
                        function render() {
                            var swipers = app.plugins.swipers;
                            $pending = swipers.pending();

                            if (swipers) {
                                swipers.attachTo($page);
                                showLoading($pending);
                                getProject()
                                        .then(prepareProjectData)
                                        .then(getMatchesForRole)
                                        .then(loadDone)
                                        .then(function () {
                                            hidePending(function () {
                                                swipers.onDataLoad(onDataLoad);
                                                swipers.showSwipers();
                                            });
                                        })
                                        .fail(function (err) {
                                            hidePending(function () {
                                                console.error(err);
                                                swipers.hide();
                                            });

                                        });

                                //TODO populate data for remaning roles
                            }
                        }

                        function show() {
                            app.reloadSwipers();
                            app.container.showContent($page);
                            render();
                        }

                        return {
                            show: show
                        };
                    }
                    app.pages[pageName] = new Page();
                }
            };
        });