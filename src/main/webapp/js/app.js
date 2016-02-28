/* global define: true,require:true */
define(['underscore', 'jquery', 'js/components/banner/banner', 'js/components/core-container/container', "text!js/plugins.json"],
        function (_, $, banner, container, PluginConfig) {
            "use strict";
            function App() {
                var pluginNames = JSON.parse(PluginConfig).plugins,
                        PLUGIN_PATH_ROOT = "js/plugins/",
                        $swipeCont = $('<article class="swipe-stack">'),
                        PLUGIN_FILE = "/plugin";
                App.prototype.init = function init() {
                    var self = this;
                    self.loadComponents();
                    self.swipers = [];
                    self.$swipeCont = $swipeCont;
                    self.SWIPE_PRELOADS = 3;
                    self.loadPlugins(pluginNames).done(function () {
                        // TODO:: Setup routing and correct views for page urls
                        self.showSwipers();
                    });
                };
                App.prototype.showSwipers = function showSwipers() {
                    var swipers = this.swipers,
                            $swiper;
                    this.container.showContent($swipeCont);
                    _.each(swipers, function (swiper) {
                        $swiper = swiper.render();
                        $swipeCont.prepend($swiper);
                        swiper.init();

                    });

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
                App.prototype.loadPlugins = function loadPlugins(pluginNames) {
                    var promise = $.Deferred(),
                            path = '',
                            app = this,
                            pluginCount = pluginNames.length;
                    _.each(pluginNames, function (pluginName) {
                        path = PLUGIN_PATH_ROOT + pluginName + PLUGIN_FILE;
                        require([path], function (plugin) {
                            plugin.init(app);
                            pluginCount--;

                            if (pluginCount <= 0) {
                                promise.resolve();
                            }
                        });
                    });
                    return promise;
                };
                App.prototype.parseTemplate = function parseTemplate(html, attributes) {
                    return _.template(html)(attributes || {});

                }

            }

            return new App();

        });
