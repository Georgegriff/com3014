/* global define: true, document: true */
define([
    'js/plugins/sidemenu/plugin',
    'js/plugins/tile/plugin',
    'js/plugins/swipers/plugin',
    'js/plugins/profile/plugin'], function (SideMenu, Tile, Swipers, Profile) {
    "use strict";
    function PluginLoader(app) {
        app.plugins = {};
        function loadPlugins() {
            var promise = $.Deferred();
            SideMenu.init(app);
            loadSwipers();
            app.reloadSwipers = loadSwipers;
            app.plugins.Tile = Tile.init(app);
            app.plugins.Profile = Profile.init(app);
            return promise.resolve();
        }

        function loadSwipers() {
            app.plugins.swipers = Swipers.init(app);
        }

        return {
            loadPlugins: loadPlugins
        };
    }
    return  PluginLoader;
});
