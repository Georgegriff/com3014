define(['jquery'], function ($) {
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
        function interceptLogout() {
            $('#logout-form').find("form").submit(function (e) {
                e.preventDefault();
                var self = this;
                saveSessionSwipes().done(function () {
                     self.submit();
                });
            });
        }
        
        function saveSessionSwipes(){
            var saveSwipes = [],
                   projectIds = app.models.matches.getProjectIds();
           
            projectIds.forEach(function(id){
                saveSwipes.push(app.models.matches.saveSwipedUsers(id)); 
            });           
            saveSwipes.push(app.models.matches.saveSwipedProjects());
       
            return $.when.apply($,saveSwipes);
        }
        
        return {
            routeHandler: function (route, event) {
                $('#content').empty();
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
                    } else if (route.indexOf("/projectmatches/") > -1) {
                        route = app.models.matches.getProjectMatchesPath("id");
                    }
                    
                    switch (route) {
                        case ROOT:
                            showPage("user-swiper", event);
                            break;
                        case app.models.user.getProfile():
                            // Save any swipes made before navigating to new screen
                            saveSessionSwipes().then(function(){
                                showPage("user-profile", event);
                            });
                            break;
                        case app.models.project.getUserProjectsPath():
                            // Save any swipes made before navigating to new screen
                            saveSessionSwipes().then(function(){
                                showPage("projects", event);
                            });
                            break;
                        case app.models.project.getProjectPath("id"):
                            showPage("project-profile", event);
                            break;
                        case app.models.matches.getProjectMatcherPath("id"):
                            showPage("project-swiper", event);
                            break;
                        case app.models.matches.getProjectMatchesPath("id"):
                            showPage("project-matches", event);
                            break;
                        default:
                            return true;
                            break;
                    }
                } else {
                    return true;
                }
            },
            interceptLogout: interceptLogout
        };
    }
    return Routing;
});