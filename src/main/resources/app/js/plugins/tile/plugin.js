/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/plugins/tile/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app) {
                    /**
                     * Tile plugin module
                     * re-usable Tile component
                     * @param {type} title
                     * @param {type} description
                     */
                    function Tile(title, description) {
                        var $tile = $(app.parseTemplate(Template, {
                            title: title, description: description
                        }));

                        function attachTo($parent) {
                            $parent.prepend($tile);
                        }
                        function setLink(href) {
                            var $links = $tile.find('.tile-link');
                            $links.prop("href", href);
                            $links.on('click', function (event) {
                                return app.routeHandler($(this).attr("href"), event);
                            });
                        }
                        function setMatchLink(href) {
                            var $links = $tile.find('.matcher-button');
                            $links.prop("href", href);
                            $links.on('click', function (event) {
                                return app.routeHandler($(this).attr("href"), event);
                            });
                        }
                        function setEditLink(href) {
                            var $links = $tile.find('.edit-button');
                            $links.prop("href", href);
                            $links.on('click', function (event) {
                                return app.routeHandler($(this).attr("href"), event);
                            });
                        }
                        function setMatchesLink(href) {
                            var $links = $tile.find('.matches-button');
                            $links.prop("href", href);
                            $links.on('click', function (event) {
                                return app.routeHandler($(this).attr("href"), event);
                            });
                        }
                        return {
                            attachTo: attachTo,
                            setLink: setLink,
                            setMatchLink: setMatchLink,
                            setMatchesLink : setMatchesLink,
                            setEditLink: setEditLink
                        };
                    }

                    return Tile;
                }
            };
        });