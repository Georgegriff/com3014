/*global require:true*/
require.config({
    baseUrl: '../',
    waitSeconds: 60,
    paths: {
        jquery: 'js/vendor/jquery.min',
        underscore: 'js/vendor/underscore-min',
        text: 'js/vendor/text',
        'jquery.ui': 'js/vendor/jquery-ui.min'
    }

});

require(['jquery', 'jquery.ui', 'js/app'], function ($, JQueryUI, app) {
    "use strict";

    function errorDialog() {
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
        }).text("Error Loading Page.");
        $dialog.addClass("has-error");
    }

    $(document).ready(function () {
        app.init();

        if ($('.has-error').length) {
            errorDialog();
        }
    });
    require.onError = function (err) {
        if (err.requireType === 'timeout') {
            console.error(err);
        } else {
            console.error(err);
        }
        errorDialog();

    };
});


