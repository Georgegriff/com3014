/* global define: true, document: true */
/**
 * User Swiper Page
 */
define(['underscore', 'jquery', 'text!js/pages/user-swiper/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app, pageName) {
                    function Page() {
                        var $page = $(app.parseTemplate(Template)),
                                $pending,
                                roleMatches = [],
                                swipes = 0,
                                MONTHS = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

                        // Triggers a save if the use navigates away from the application
                        $(window).bind('unload', function () {
                            app.models.matches.saveSwipedProjects(app.currentUser.userId);
                        });

                        function onDataLoad(swiper) {
                            if (roleMatches.length) {
                                app.plugins.swipers.show();
                                if (swiper) {
                                    var project = roleMatches[swiper.getPosition()],
                                            projectRole = null,
                                            role = null;
                                    if (project) {
                                        projectRole = project.rolesList[0];
                                        role = projectRole.role;
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

                                        swiper.addList("Required Skills", projectRole.skillsList, function (skill) {
                                            return skill.skill.name;
                                        });
                                        swiper.addList("Required Qualifications", projectRole.qualificationsList, function (qualification) {
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
                                var obj = new Object();
                                obj.project = projectId;
                                obj.role = roleId;
                                app.models.matches.addToProjectsAccepted(obj);

                                swipes++;
                                checkSwipeThreshold();
                                // remove current entry from array
                                roleMatches.shift();
                            };
                        }
                        function onReject(projectId, roleId) {
                            return function () {
                                // save to rejected array
                                var obj = new Object();
                                obj.project = projectId;
                                obj.role = roleId;
                                app.models.matches.addToProjectsRejected(obj);

                                swipes++;
                                checkSwipeThreshold();
                                // remove current entry from array
                                roleMatches.shift();
                            };
                        }
                        function checkSwipeThreshold() {
                            if (swipes > 5) {
                                app.models.matches.saveSwipedProjects(app.currentUser.userId);
                                swipes = 0;
                            }
                            return;
                        }
                        function getMatches() {
                            return app.models.matches.getMatchesForUser();
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

                        function render() {
                            var swipers = app.plugins.swipers;
                            $pending = swipers.pending();
                            roleMatches = [];
                            if (swipers) {
                                swipers.attachTo($page);
                                showLoading($pending);
                                getMatches()
                                        .then(loadDone)
                                        .then(function () {
                                            hidePending(function () {
                                                app.container.showContent($page);
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