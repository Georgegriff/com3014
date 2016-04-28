/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/pages/projects/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app, pageName) {

                    function Page() {
                        var $page = null;

                        function render() {
                            if (app.currentUser) {
                                app.models.project.getUserProjects(app.currentUser.userId)
                                        .then(showProjects)
                                        .then(showAddTile);
                            }
                        }
                        function showAddTile() {
                            $('#create-project, #add-project').click(function(e){
                                 return app.routeHandler(app.models.project.createProjectPath(), e); 
                            });
                        }
                        function showProjects(projects) {
                            if (projects && projects.length) {
                                projects.forEach(function (project) {
                                    if (project) {
                                        createProjectTile(project);
                                    }
                                });
                            }
                            return $.Deferred().resolve();
                        }
                        function createProjectTile(project) {
                            var $tiles = $('.tiles'),
                                    tile = new app.plugins.Tile(project.name, project.description);
                            tile.setLink(app.models.project.getProjectPath(project.projectId));
                            tile.attachTo($tiles);
                            
                            tile.setMatchLink(app.models.matches.getProjectMatcherPath(project.projectId));
                            
                            tile.setMatchesLink(app.models.matches.getProjectMatchesPath(project.projectId));
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