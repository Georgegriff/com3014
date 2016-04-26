/* global define: true, document: true */
define(['jquery'],
        function ($) {
            "use strict";
            var csrf = $("meta[name='_csrf']").attr("content");
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
                    var promise = $.Deferred();
                    $.ajax({
                        type: "POST",
                        headers : {
                          "X-CSRF-TOKEN" :   csrf
                        },
                        url: "/services" + url,
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({preferences : [accepted, rejected]}),
                        dataType: "json"
                    }).always(function(){
                        return promise.resolve();
                    });
                }
            };
        });