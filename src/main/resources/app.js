/* global define: true,require:true */
define(['underscore', 'jquery', 'js/components/banner/banner', 'js/components/core-container/container', "text!js/plugins.json"],
        function (_, $, banner, container, PluginConfig) {
            "use strict";
            function App() {
                var ROOT = "/",
                        self = this;
                App.prototype.init = function init() {
                    self.loadComponents();
                    self.loadPlugins()
                            .done(initRouteHandler);
                    self.pages = {};
                };

                function initRouteHandler() {
                    $('a').on('click', function (event) {
                        return self.routeHandler($(this).attr("href"), event);
                    });
                    window.onpopstate = function popSate(event) {
                        return self.routeHandler(window.location.pathname, event);
                    };
                    self.routeHandler(window.location.pathname);
                }

                App.prototype.routeHandler = function routeHandler(route, event) {
                    if (typeof (route) !== 'undefined' && route) {

                        switch (route) {
                            case ROOT:
                                showPage("user-swiper", event);
                                break;
                            default:
                                return true;
                                break;
                        }
                    } else {
                        return true;
                    }


                };
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
                    if (!self.pages[name]) {
                        return self.requireModule(PAGE_NAME_ROOT, name, PAGE_FILE)
                                .then(function () {
                                    return self.pages[name];
                                });
                    } else {
                        return getPage();
                    }

                }
                function getPage() {
                    return $.Deferred().resolveWith(self.pages[name]);
                }
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
