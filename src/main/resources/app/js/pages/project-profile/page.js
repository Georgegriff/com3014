/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/pages/project-profile/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app, pageName) {

                    function Page() {
                        var $page = null,
                                MONTHS = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

                        function render(project) {
                            setupProfile(project)
                            var $links = $page.find('a');
                            $links.on('click', function (event) {
                                return app.routeHandler($(this).attr("href"), event);
                            });

                        }
                        function monthYearRange(start, end) {
                            var start = new Date(start),
                                    end = new Date(end);

                            return MONTHS[start.getMonth()] + " " + start.getFullYear() + " - " + MONTHS[end.getMonth()] + " " + end.getFullYear();
                        }
                        function addEstimatedDate(start, end) {
                            var $projData = $page.find('.project-data');
                            $projData.append('<div class="prof-detail">Estmated Project Timeline: ' + monthYearRange(start, end) + '</div>');
                        }
                        function show() {
                            $.getJSON("/services/" + window.location.pathname)
                                    .then(function (project) {
                                        $page = $(app.parseTemplate(Template, {
                                            projectsLink: app.models.project.getUserProjectsPath(app.currentUser.userId),
                                            name: project.name
                                        }));
                                        app.container.showContent($page);
                                        console.log(project);
                                        render(project);

                                    });

                        }
                        
                         function addList(listTitle, items, itemHandler) {
                            var $pContainer = $profile.find('.profile-page-info'),
                                    $plist = $('<div class="profile-list-cont">'),
                                    $listTitle = $('<h4 class="profile-list">' + listTitle + "</h4>"),
                                    $list = $('<ul class="profile-list">');
                            $pContainer.append($plist);
                            $plist.append($listTitle);
                            $plist.append($list);
                            if (_.isArray(items)) {
                                items.forEach(function (item) {
                                    if (_.isFunction(itemHandler)) {
                                        $list.append('<li>' + itemHandler(item) + '</li>');
                                    } else {
                                        $list.append('<li>' + item + '</li>');
                                    }

                                });
                            }

                        }
                        
                        function renderRole(role, profile) {
                            profile.addList("Skills", role.skillsList, function (skill) {
                                return skill.skill.name + ", " + skill.monthsOfExperience + " Months Experience.";
                            });
                            profile.addList("Required Qualifications", role.qualificationsList, function (qualification) {
                                return qualification.subject + ": " + qualification.qualificationLevel.qualificationLevel;
                            });

                            profile.addList("Required Skills", role.skillsList, function (skill) {
                                return skill.skill.name + ", " + skill.monthsOfExperience + " Months Experience.";
                            });
                        }
                        function setupProfile(project) {
                            var profile = new app.plugins.Profile({
                                image: 'img/default-project.svg',
                                contact: project.email
                            }), roles = project.rolesList;
                            profile.addDescription(project.description);
                            addEstimatedDate(project.projectStart, project.estimatedEnd);
                            profile.addList("Related Interests", project.interestsList, function (interest) {
                                return interest.interest;
                            });
                            roles.forEach(function (role) {
                                renderRole(role, profile);
                            });



                            profile.attachTo($page.find('.project-data'));
                            console.log(project);
                        }
                        return {
                            show: show
                        };
                    }
                    app.pages[pageName] = new Page();
                }
            };
        });
