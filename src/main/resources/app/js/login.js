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
require(['underscore', 'jquery', 'jquery.ui', 'js/plugins/form/plugin'], function (_, $, JQueryUI, SiteForms) {

    function LoginPage() {

        var $loginPage, $username, $password, $button;
        function render() {
            $loginPage = $('#login-page'),
                    $username = $('#username'),
                    $password = $('#password'),
                    $button = $('#login-btn');
            listenToInput($username);
            listenToInput($password);
            registerButton();
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
                $registerBtn = $('#register-btn'),
                $loginRoot = $('#login-page'),
                SiteForm = SiteForms.init({
                    parseTemplate: function parseTemplate(html, attributes) {
                        return _.template(html)(attributes || {});
                    }}),
                form = new SiteForm({
                    title: "Register",
                    name: "registerform",
                    description: "Sign up to ProMatch."
                });
        form.attachTo($registerPage);
        var firstName = form.addField({label: "First Name", validator: function (value) {
                return value && value !== "";
            }}),
                lastName = form.addField({label: "Last Name", validator: function (value) {
                        return value && value !== "";
                    }}),
                email = form.addField({type: "email", label: "Email", validator: function (value) {
                        return value && value !== "";
                    }}),
                username = form.addField({label: "Username", validator: function (value) {
                        return value && value !== "";
                    }}),
                location = form.addField({label: "Location", validator: function (value) {
                        return value && value !== "";
                    }}),
                qualifications = form.additionButton({id: "add-qual", label: "Add Qualification", action: function (e) {
                        form.addComboAndField({subElement: "#add-qual", validator: function (value) {
                                return value && value !== "";
                            }});
                    }}),
                skills = form.additionButton({id: "add-skill", label: "Add Skill", action: function (e) {
                        form.addComboAndField({subElement: "#add-skill", validator: function (value) {
                                return value && value !== "";
                            }});
                    }}),
                interests = form.additionButton({id: "add-inter", label: "Add Interest", action: function (e) {
                        form.addField({subElement: "#add-inter", label: "Interest", validator: function (value) {
                                return value && value !== "";
                            }});
                    }});
        form.addButton({label: "Register", action: function (e) {
                e.preventDefault();
                registerUser();
            }});



        $registerBtn.click(function (e) {
            render();
        });
        function getRegistrationData() {
            return {
                username: "1",
                name: "2",
                forename: "3",
                surname: "4",
                email: "5"


            };
        }
        function registerUser() {
            $.ajax({
                type: "POST",
                headers: {
                    "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content"),
                },
                url: "/register",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(getRegistrationData()),
                dataType: "json"
            }).success(function () {
                showLogin();
            }).fail(function (e) {
                form.showError("Validation Error");
            });
        }

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
        };
    }


    $(document).ready(function () {
        window.scrollTo(0, 1);
        var login = new LoginPage();
        login.render();
        var registerPage = new RegisterPage(login);
        if (login.hasError()) {
            login.shake();
        }


    }
    );
});


