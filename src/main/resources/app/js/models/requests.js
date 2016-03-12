/* global define: true, document: true */
define(['jquery'],
        function ($) {
            return {
                getJSON: function (url) {
                    return $.getJSON("/services" + url).fail(function(e){
                       if(e.status === 403){
                           window.location.reload();
                       }
                    });
                }
            };
        });