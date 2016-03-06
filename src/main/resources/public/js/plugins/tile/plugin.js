/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/plugins/tile/template/template.html'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app) {
                    
                    function Tile(title, description){
                          var $tile = $(app.parseTemplate(Template, {
                              title: title, description: description
                          }));
                          
                          function attachTo($parent){
                              $parent.prepend($tile);
                          }
                          return {
                              attachTo : attachTo
                          };
                    }
                    
                    app.Tile = Tile;
                }
            };
        });