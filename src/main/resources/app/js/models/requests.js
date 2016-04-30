/* global define: true, document: true */
define(['jquery'],
        function ($) {
            "use strict";
            var csrf = $("meta[name='_csrf']").attr("content");

            function errorDialog(message) {
                var $cont = $('<div id="error-dialog">');
                $('body').prepend($cont);
                var $dialog = $("#error-dialog");
                $dialog.dialog({
                    dialogClass: "no-close",
                    buttons: [
                        {
                            text: "OK",
                            click: function () {
                                $(this).dialog("close");
                                $(this).removeClass("has-error");
                                $(this).remove();
                            }
                        }
                    ]
                }).text(message || "An Error Has Occurred.");
                $dialog.addClass("has-error");
            }
            $.ajaxSetup({
                error: function (x, status, error) {
                    if (x.status.toString().charAt(0) === "4" || x.status.toString().charAt(0) === "5") {
                        if (x.status === 404) {
                            errorDialog("Page Not Found.");
                        } else {
                            errorDialog(x.responseText);
                        }
                    }
                }
            });
            return {
                getJSON: function (url) {
                    return $.get("/services" + url).fail(function (e) {
                        if (e.status === 403) {
                            window.location.reload();
                        }
                    }).then(function (data) {
                        return data;
                    });
                },
                postPreferences: function (url, accepted, rejected) {
                    var promise = $.Deferred();
                    $.ajax({
                        type: "POST",
                        headers: {
                            "X-CSRF-TOKEN": csrf
                        },
                        url: "/services" + url,
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({preferences: [accepted, rejected]}),
                        dataType: "json"
                    }).always(function () {
                        return promise.resolve();
                    });
                }
            };
        });