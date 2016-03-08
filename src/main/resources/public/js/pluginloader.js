/* global define: true, document: true */
define([
    'js/plugins/sidemenu/plugin',
    'js/plugins/tile/plugin',
    'js/plugins/swipers/plugin'], function (SideMenu, Tile, Swipers) {
    "use strict";
    function PluginLoader(app) {
        app.plugins = {};
        function loadPlugins() {
            var promise = $.Deferred();
            SideMenu.init(app);
            app.plugins.swipers = Swipers.init(app);
            app.plugins.Tile = Tile.init(app);
            return promise.resolve();
        }
        return {
            loadPlugins: loadPlugins
        };

    }
    return  PluginLoader;



});
