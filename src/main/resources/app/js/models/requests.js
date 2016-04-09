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
                },
                putJSON: function (url, accepted, rejected) {
                    return $.ajax({
                        type: "PUT",
                        url: "/services" + url,
                        data: {accepted: accepted, rejected: rejected},
                        dataType: "json"
                    });
                }
            };
        });