/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/pages/project-profile/template/template.html'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app, pageName) {

                    function Page() {
                        var $page = null;

                        function render() {
                            var $links = $page.find('a');
                            $links.on('click', function (event) {
                                return app.routeHandler($(this).attr("href"), event);
                            });

                        }

                        function show() {
                            $.getJSON("/services/" + window.location.pathname)
                                    .then(function (project) {
                                        $page = $(app.parseTemplate(Template, {
                                            projectsLink: app.models.project.getUserProjectsPath(app.currentUser.userId),
                                            name: project.name
                                        }));
                                        app.container.showContent($page);
                                        render();
                                    });

                        }

                        return {
                            show: show
                        };
                    }
                    app.pages[pageName] = new Page();
                }
            };
        });