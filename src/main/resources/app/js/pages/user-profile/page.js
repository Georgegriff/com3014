/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/pages/user-profile/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app, pageName) {

                    function Page() {
                        var $page = null,
                                $profile;

                        function render() {
                            if (app.currentUser) {
                                setupProfile();
                            }
                        }
                        function setupProfile() {
                            var user = app.currentUser,
                                    profile = new app.plugins.Profile({
                                        name: user.name,
                                        contact: user.email
                                    });
                            profile.addList("Qualifications", user.qualificationsList, function (qualification) {
                                return qualification.subject + ": " + qualification.qualificationLevel.qualificationLevel;
                            });
                            profile.addList("Interests", user.interestsList, function (interest) {
                                return interest.interest;
                            });
                            profile.addList("Skills", user.skillsList, function (skill) {
                                return skill.skill.name + ", " + skill.monthsOfExperience + " Months Experience.";
                            });

                            profile.attachTo($page);
                            console.log(user);
                        }

                        function show() {
                            $page = $(app.parseTemplate(Template));
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