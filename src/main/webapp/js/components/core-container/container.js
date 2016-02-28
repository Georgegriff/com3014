/* global define: true */
define(['jquery'],
        function ($) {
            "use strict";
            return {
                init: function (app) {          
                    function Container() {
                        var $container = $('#container');   
                        
                        function showContent($element) {
                           $container.empty();
                           $container.append($element);
                        }
                        return {                 
                            showContent: showContent
                        };
                    }
                    app.container = new Container();             
                }
            }


        });
