define(function () {
    var ROOT = "/";
    function Routing(app) {
        function historyAPI() {
            return !!(window.history && history.pushState);
        }
        function showPage(name, event) {
            if (event) {
                event.preventDefault();
            }
            loadPage(name).then(function (page) {
                page.show();
            });
        }
        function loadPage(name) {
            var PAGE_NAME_ROOT = "js/pages/",
                    PAGE_FILE = "/page";

            // check for cached page
            if (!app.pages[name]) {
                return app.requireModule(PAGE_NAME_ROOT, name, PAGE_FILE)
                        .then(function () {
                            return app.pages[name];
                        });
            } else {
                return getPage(name);
            }

        }
        function getPage(name) {
            return $.Deferred().resolve(app.pages[name]);
        }
        return {
            routeHandler: function (route, event) {                
                if (typeof (route) !== 'undefined' && route) {
                    if (historyAPI()) {
                        if (route !== window.location.pathname) {
                            history.pushState(null, null, route);
                        }
                    }
                    if (route.indexOf("/matches/") > -1) {
                        
                        if (route.indexOf("/project/") > -1) {
                            route = app.models.matches.getProjectMatcherPath("id");
                        }
                    } else if (route.indexOf("/project/") > -1) {
                        route = app.models.project.getProjectPath("id");
                    }
                    
                    var projectId = app.models.matches.getProjectId();
                    
                    switch (route) {
                        case ROOT:
                            showPage("user-swiper", event);
                            break;
                        case app.models.user.getProfile():
                            // Save any swipes made before navigating to new screen
                            app.models.matches.saveSwipedUsers(projectId);
                            app.models.matches.saveSwipedProjects(app.currentUser.userId);
                            
                            showPage("user-profile", event);
                            break;
                        case app.models.project.getUserProjectsPath(app.currentUser.userId):
                            // Save any swipes made before navigating to new screen
                            app.models.matches.saveSwipedUsers(projectId);
                            app.models.matches.saveSwipedProjects(app.currentUser.userId);
                            
                            showPage("projects", event);
                            break;
                        case app.models.project.getProjectPath("id"):
                            showPage("project-profile", event);
                            break;
                        case app.models.matches.getProjectMatcherPath("id"):
                            showPage("project-swiper", event);
                            break;
                        default:
                            return true;
                            break;
                    }
                } else {
                    return true;
                }
            }
        };
    }
    return Routing;
});