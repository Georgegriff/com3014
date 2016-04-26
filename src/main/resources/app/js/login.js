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
require(['jquery', 'jquery.ui'], function ($,JQueryUI) {

    function LoginPage() {

        var $loginPage, $username, $password, $button;
        function render() {
            $loginPage = $('#login-page'),
                    $username = $('#username'),
                    $password = $('#password'),
                    $button = $('#login-btn');
            listenToInput($username);
            listenToInput($password);
            registerButton()
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

        function registerButton() {
                var $registerBtn = $('<a id="register-btn">Register</a>');
                $registerBtn.insertAfter($('#login-btn'));
        }


        function hide() {
            $loginPage.find("*").hide();
        }
        function show() {
            $loginPage.find("*").show();
        }

        return {
            render: render,
            hasError: hasError,
            shake: shake,
            hide: hide
        };
    }


    function RegisterPage(loginPage) {
        var $registerPage = $('<section id="register-page">'),
                $loginRoot = $('#login-page'),
                SiteForm = SiteForms.init({
                    parseTemplate: function parseTemplate(html, attributes) {
                        return _.template(html)(attributes || {});
                    }}),
                form = new SiteForm({
                    title: "Register",
                    name: "register-form",
                    description: "Sign up to ProMatch."
                });
        form.attachTo($registerPage);
        function render() {
            loginPage.hide();
            $loginRoot.prepend($registerPage);
        }

        function hide() {
            $registerPage.remove();
        }


        function showLogin() {
            hide();
            loginPage.show();
        }

        return {
            render: render,
            hide: hide
        }
    }


    $(document).ready(function () {
        window.scrollTo(0, 1);
        var login = new LoginPage();
         //       registerPage = new RegisterPage(login);
        login.render();
        if (login.hasError()) {
            login.shake();
        }


    }
    );
});


