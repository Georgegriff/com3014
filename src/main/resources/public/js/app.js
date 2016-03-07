/* global define: true,require:true */
define(['underscore',
    'jquery',
    'js/components/banner/banner',
    'js/components/core-container/container',
    "text!js/plugins.json",
    "js/routing",
    "js/models/user",
    "js/models/project"],
        function (_, $, banner, container, PluginConfig, Routing, UserModel, ProjectModel) {
            "use strict";
            function App() {
                var self = this,
                        routing = null;
                App.prototype.init = function init() {
                    self.initModels();
                    self.loadComponents();
                    routing = new Routing(self);
                    self.initialiseUser()
                            .then(self.loadPlugins)
                            .then(initRouteHandler);
                    self.pages = {};

                };
                App.prototype.initialiseUser = function initUser() {
                    return UserModel.getCurrentUser().then(function (user) {
                        self.currentUser = user;
                    });

                }
                App.prototype.initModels = function initModels() {
                    self.models = {};
                    self.models.user = UserModel;
                    self.models.project = ProjectModel;
                }
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
                }
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
                 *@param {Array} pluginNames pluginName
                 */
                App.prototype.loadPlugins = function loadPlugins() {
                    var pluginNames = JSON.parse(PluginConfig).plugins,
                            PLUGIN_PATH_ROOT = "js/plugins/",
                            PLUGIN_FILE = "/plugin";
                    return self.requireModule(PLUGIN_PATH_ROOT, pluginNames, PLUGIN_FILE);
                };
                App.prototype.parseTemplate = function parseTemplate(html, attributes) {
                    return _.template(html)(attributes || {});

                };


            }

            return new App();

        });
