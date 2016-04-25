/* global define: true, document: true */
define(['jquery'],
        function ($) {
            return {
                getJSON: function (url) {
                    return $.get("/services" + url).fail(function(e){
                       if(e.status === 403){
                           window.location.reload();
                       }
                    }).then(function(data){
                        return data;
                    });
                },
                postPreferences: function (url, accepted, rejected) {
                    return $.ajax({
                        type: "POST",
                        url: "/services" + url,
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({preferences : [accepted, rejected]}),
                        dataType: "json"
                    });
                }
            };
        });