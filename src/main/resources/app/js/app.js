/* global define: true,require:true */
define(['underscore',
    'jquery',
    'jquery.ui',
    'js/components/banner/banner',
    'js/components/core-container/container',
    "js/routing",
    "js/pluginloader",
    "js/models/user",
    "js/models/project",
    "js/models/matches"],
        function (_, $, JQueryUI, banner, container, Routing, PluginLoader, UserModel, ProjectModel, MatchesModel) {
            "use strict";
            /**
             * 
             * Core Application Module
             */
            function App() {
                var self = this,
                        routing = null,
                        pluginLoader = null;
                App.prototype.init = function init() {
                    self.initModels();
                    self.loadComponents();
                    routing = new Routing(self);
                    routing.interceptLogout();
                    pluginLoader = new PluginLoader(self);
                    self.initialiseUser()
                            .then(self.loadPlugins)
                            .then(self.linkUserProfile)
                            .then(initRouteHandler);
                    self.pages = {};

                };
                App.prototype.initialiseUser = function initUser() {
                    return self.models.user.getUser().then(function (user) {
                        self.currentUser = user;
                    });

                };
                App.prototype.linkUserProfile = function linkUserProfile() {
                    //var uri = self.models.user.getProfile(self.currentUser.userId);
                    var uri = self.models.user.getProfile();
                    $('#profile-icon').find('a').prop("href", uri);
                };
                App.prototype.initModels = function initModels() {
                    self.models = {};
                    self.models.user = UserModel;
                    self.models.project = ProjectModel;
                    self.models.matches = MatchesModel;
                };
                function initRouteHandler() {
                    $('a').on('click', function (event) {
                        return self.routeHandler($(this).attr("href"), event);
                    });
                    window.onpopstate = function popState(event) {
                        return self.routeHandler(window.location.pathname, event);
                    };
                    self.routeHandler(window.location.pathname);
                    return $.Deferred().resolve();
                }

                App.prototype.routeHandler = function routeHandler(route, event) {
                    routing.routeHandler(route, event);
                };


                /**
                 * Lazy load a module
                 * @param {type} path
                 * @param {type} names
                 * @param {type} filename
                 * @returns {Deferred}
                 */
                App.prototype.requireModule = function requireModule(path, names, filename) {
                    names = _.isArray(names) ? names : [names];
                    var promise = $.Deferred(),
                            fullPath,
                            app = this,
                            moduleCount = names.length;
                    _.each(names, function (moduleName) {
                        fullPath = path + moduleName + filename;
                        require([fullPath], function (module) {
                            module.init(app, moduleName);
                            moduleCount--;

                            if (moduleCount <= 0) {
                                promise.resolve();
                            }
                        });
                    });
                    return promise;
                };
                /**
                 * 
                 *Initialise core components that can have plugins attached to
                 */
                App.prototype.loadComponents = function loadComponents() {
                    banner.init(this);
                    container.init(this);
                };
                /**
                 * 
                 *Asyncronously load plugins
                 */
                App.prototype.loadPlugins = function loadPlugins() {
                    return pluginLoader.loadPlugins();
                };
                App.prototype.parseTemplate = function parseTemplate(html, attributes) {
                    return _.template(html)(attributes || {});

                };

                App.prototype.setHelpTips = function setHelpTips(html) {
                    var $help = $("#help-tips");
                    $help.attr("title", html);
                            $help.tooltip({
                        content : function(){
                            return html;
                        },
                        show: {
                            effect: "slideDown",
                            delay: 100,
                            duration  : 300
                        }
                    });

                };


            }

            return new App();

        });
