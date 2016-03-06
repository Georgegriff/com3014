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
                    switch (route) {
                        case ROOT:
                            showPage("user-swiper", event);
                            break;
                        case app.models.user.getProfile():
                            showPage("user-profile", event);
                            break;
                        case app.models.project.getUserProjectsPath(app.currentUser.userId):
                            showPage("projects", event);
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