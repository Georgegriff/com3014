/* global define: true */
define(['jquery'],
        function ($) {
            "use strict";
            return {
                init: function (app) {          
                    function Container() {
                        var $content = $('#content');   
                        
                        function showContent($element) {
                           $content.empty();
                           $content.append($element);
                        }
                        return {                 
                            showContent: showContent
                        };
                    }
                    app.container = new Container();             
                }
            }


        });
