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
                                roles = [],
                                swipes = 0,
                                MONTHS = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
                        
                        // Triggers a save if the use navigates away from the application
                        $(window).bind('unload', function() {
                            app.models.matches.saveSwipedProjects(app.currentUser.userId);
                        });
                                               
                        function onDataLoad(swiper) {
                            if (roleMatches.length) {
                                app.plugins.swipers.show();
                                if (swiper) {
                                    //TODO:: check if roleMatches array is < threshold if so repopulate;
                                    var project = roleMatches[swiper.getPosition()],
                                            role = null;
                                    if (project) {
                                        role = project.rolesList[0];
                                        swiper.init({
                                            onAccept: onAccept(project.projectId, role.roleId),
                                            onReject: onReject(project.projectId, role.roleId)
                                        });

                                        swiper.setImage("img/default-project.svg");
                                        swiper.setTitle(project.name);
                                        swiper.setSubtitle(role.name);
                                        swiper.addDescription(project.description);
                                        swiper.addList("Interests", project.interestsList, function (interest) {
                                            return interest.interest;
                                        });
                                        swiper.setRightSubtitle("Estmated Project Timeline:<br/>" +
                                                monthYearRange(project.projectStart, project.estimatedEnd));

                                        swiper.addList("Required Skills", role.skillsList, function (skill) {
                                            return skill.skill.name;
                                        });
                                        swiper.addList("Required Qualifications", role.qualificationList, function (qualification) {
                                            return qualification.subject + ": " + qualification.qualificationLevel.qualificationLevel;
                                        });
                                    }
                                }
                            } else {
                                app.plugins.swipers.hide();
                            }
                        }

                        function monthYearRange(start, end) {
                            var start = new Date(start),
                                    end = new Date(end);

                            return MONTHS[start.getMonth()] + " " + start.getFullYear() + " - " + MONTHS[end.getMonth()] + " " + end.getFullYear();
                        }

                        function onAccept(projectId, roleId) {
                            return function () {
                                 // save to accepted array
                                app.models.matches.addToProjectsAccepted(projectId);
                                swipes++;
                                checkSwipeThreshold();
                                // remove current entry from array
                                roleMatches.shift();
                            };
                        }
                        function onReject(projectId, roleId) {
                            return function () {
                                 // save to rejected array
                                app.models.matches.addToProjectsRejected(projectId);
                                swipes++;
                                checkSwipeThreshold();
                                // remove current entry from array
                                roleMatches.shift();
                            };
                        }
                        function checkSwipeThreshold() {
                            if(swipes > 5) {
                                app.models.matches.saveSwipedProjects(app.currentUser.userId);
                                swipes = 0;
                            }
                            return;
                        }
                        function getMatches() {
                            return app.models.matches.getMatchesForUser();
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

                        function hidePending(action) {
                            return $pending.fadeOut(1000, function () {
                                $pending.remove();
                                if (_.isFunction(action)) {
                                    action();
                                }
                            });
                        }
                        function loadDone(data) {
                            roleMatches = data;
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
                                getMatches()
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