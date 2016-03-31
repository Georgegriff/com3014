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
require.onError = function (err) {
    if (err.requireType === 'timeout') {
        alert("error: " + err);
    } else {
        throw err;
    }
};
require(['jquery', 'jquery.ui'], function ($) {
    "use strict";

    function LoginPage() {

        var $loginPage, $username, $password, $button;

        function render() {
            $loginPage = $('#login-page'),
                    $username = $('#username'),
                    $password = $('#password'),
                    $button = $('#login-btn');
            listenToInput($username);
            listenToInput($password);
        }

        function hasError() {
            return window.location.search.indexOf("?error") > -1;
        }

        function shake() {
            $loginPage.effect("shake", {
                distance: 5
            });
        }


        function listenToInput($input, action) {
            $input.on("focus change keyup", function (e) {
                if ($username.val() && $password.val()) {
                    $button.prop('disabled', false);
                } else if (!$button.is(":disabled")) {
                    $button.prop('disabled', true);
                }
            });
        }

        return {
            render: render,
            hasError: hasError,
            shake: shake
        };
    }


    $(document).ready(function () {
        window.scrollTo(0,1);
        var login = new LoginPage();
        login.render();
        if (login.hasError()) {
            login.shake();
        }


    });
});


