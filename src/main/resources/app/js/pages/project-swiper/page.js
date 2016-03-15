/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/pages/project-swiper/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app, pageName) {
                    function Page() {
                        var $page = $(app.parseTemplate(Template)),
                                $pending,
                                roleMatches = [],
                                project = {},
                                roles = [];
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
                                            onAccept: onAccept(),
                                            onReject: onReject()
                                        });
                                        swiper.setImage("img/default-profile.svg");
                                        swiper.setTitle(user.name);
                                        swiper.setSubtitle(role.name);
                                        swiper.setRightSubtitle("Last Active " + lastActive(user.lastLogin) + " Days Ago");
                                        //swiper.addDescription("About");
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
                                // remove current entry from array
                                roleMatches.shift();
                            };
                        }
                        function onReject(userId, roleId) {
                            return function () {
                                // remove current entry from array
                                roleMatches.shift();
                            };
                        }
                        function getProject() {
                            var path = window.location.pathname,
                                    splitPath = path.split("/"),
                                    id = splitPath[splitPath.length - 1];
                            return app.models.project.getProject(id);
                        }
                        function prepareProjectData(data) {
                            project = data;
                            roles = project.rolesList;
                            return roles;
                        }
                        function showLoading($pending) {
                            $('#content').append($pending);
                            $pending.fadeIn();
                        }
                        function getMatchesForRole(roles) {
                            if (roles.length > 0) {
                                var firstRole = roles[0];
                                return app.models.matches.getMatchesForRole(firstRole.roleId)
                                        .then(function (data) {
                                            return $.Deferred().resolve(firstRole, data);
                                        }).fail(function (err) {
                                    console.error(err);
                                });
                            }
                            return null;
                        }
                        function loadDone(role, data) {
                            $pending.fadeOut(1000, function(){
                                $pending.remove();
                            });
                            if (_.isArray(data)) {
                                _.each(data, function(item){
                                   if(item){
                                       item.matchedRole = role;
                                   } 
                                });
                                roleMatches = data;
                            }
                        }
                        function loadNewData() {
                            // TODO
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
                                            swipers.onDataLoad(onDataLoad);
                                            swipers.showSwipers();
                                        })
                                        .fail(function (err) {
                                            console.error(err);
                                        });

                                //TODO populate data for remaning roles
                            }
                        }

                        function show() {
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