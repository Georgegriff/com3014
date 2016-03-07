/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/plugins/tile/template/template.html'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app) {

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

                        return {
                            attachTo: attachTo,
                            setLink: setLink
                        };
                    }

                    app.Tile = Tile;
                }
            };
        });