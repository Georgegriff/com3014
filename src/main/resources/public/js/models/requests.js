/* global define: true, document: true */
define(['jquery'],
        function ($) {
            return {
                getJSON: function (url) {
                    return $.getJSON("/services" + url);
                }
            }
        });