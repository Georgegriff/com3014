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
              $loginPage.removeClass("register-pg");
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
        var skills = [],
                qualifications = [],
                $registerPage = $('<section id="register-page">'),
                $registerBtn = $('#register-btn'),
                $loginRoot = $('#login-page'),
                SiteForm = SiteForms.init({
                    parseTemplate: function parseTemplate(html, attributes) {
                        return _.template(html)(attributes || {});
                    }}),
                form = new SiteForm({
                    title: "Register",
                    name: "registerform",
                    description: '<a href="/login" class="login-link">Login</a><span>Sign up to ProMatch.</span>'
                }); 
        form.attachTo($registerPage);
        var $firstName = form.addField({label: "First Name", validator: function (value) {
                return value && value !== "";
            }}),
                $lastName = form.addField({label: "Last Name", validator: function (value) {
                        return value && value !== "";
                    }}),
                $email = form.addField({type: "email", label: "Email", validator: function (value) {
                        return value && value !== "";
                    }}),
                $username = form.addField({label: "Username", validator: function (value) {
                        return value && value !== "";
                    }}),
                $password = form.addField({type: "password", label: "Password", validator: function (value) {
                        return value && value !== "";
                    }}),
                $location = form.addField({label: "Post Code", validator: function (value) {
                        return value && value !== "";
                    }}),
                $qualifications = form.additionButton({id: "add-qual", label: "Add Qualification", action: function (e) {
                        form.addComboAndField({subElement: "#add-qual", "label": "Qualification Title", validator: function (value) {
                                return value && value !== "";
                            }});
                        form.populateCombo($qualifications.find("select"), qualifications, function (qual) {
                            return {
                                value: qual.qualificationId,
                                name: qual.qualificationLevel
                            };
                        });
                    }}),
                $skills = form.additionButton({id: "add-skill", label: "Add Skill", action: function (e) {
                        form.addComboAndField({subElement: "#add-skill", "label": "Months Experience", validator: function (value) {
                                return value && value !== "";
                            }});
                        form.populateCombo($skills.find("select"), skills, function (skill) {
                            return {
                                value: skill.skillId,
                                name: skill.name
                            };
                        });
                    }}),
                $interests = form.additionButton({id: "add-inter", label: "Add Interest", action: function (e) {
                        form.addField({subElement: "#add-inter", label: "Interest", validator: function (value) {
                                return value && value !== "";
                            }});
                    }}),
                fields = {
                    $firstName: $firstName,
                    $lastName: $lastName,
                    $email: $email,
                    $username: $username,
                    $password: $password,
                    $location: $location,
                    $qualifications: $qualifications,
                    $skills: $skills,
                    $interests: $interests
                };
        form.addButton({label: "Register", action: function (e) {
                e.preventDefault();
                registerUser(fields);
            }});
        getAllSkillsAndQualifications();



        $registerBtn.click(function (e) {
            render();
        });

        function getAllSkillsAndQualifications() {
            return $.getJSON("/services/userskills").then(function (data) {
                if (data) {
                    skills = data.skills && data.skills.length ? data.skills : [];
                    qualifications = data.qualifications && data.qualifications.length ? data.qualifications : [];
                }
            });
        }

        function getRegistrationData(fields) {
            return {
                password: fields.$password.find('input').val(),
                user: {
                    forename: fields.$firstName.find('input').val(),
                    surname: fields.$lastName.find('input').val(),
                    email: fields.$email.find('input').val(),
                    username: fields.$username.find('input').val(),
                    location: {stringLocation: fields.$location.find('input').val()
                    },
                    skillsList: function (values) {
                        var output = [];
                        if (values && values.length) {
                            values.forEach(function (value) {
                                output.push({
                                    "monthsOfExperience": value.inputValue,
                                    "skill": {
                                        skillId: value.comboValue.value,
                                        name: value.comboValue.text
                                    }
                                });
                            });
                        }
                        return output;

                    }(form.getComboFieldValues(fields.$skills)),
                    qualificationsList: function (values) {
                        var output = [];
                        if (values && values.length) {
                            values.forEach(function (value) {
                                output.push({
                                    "subject": value.inputValue,
                                    "qualificationLevel": {
                                        qualificationId: value.comboValue.value,
                                        qualificationLevel: value.comboValue.text
                                    }
                                });
                            });
                        }
                        return output;

                    }(form.getComboFieldValues(fields.$qualifications)),
                    interestsList: function (values) {
                        var output = [];
                        if (values && values.length) {
                            values.forEach(function (value) {
                                output.push({
                                    "interest": value
                                });
                            });
                        }
                        return output;

                    }(form.getFieldValues(fields.$interests))
                }


            };
        }
        function registerUser(fields) {
            $.ajax({
                type: "POST",
                headers: {
                    "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content"),
                },
                url: "/register",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(getRegistrationData(fields)),
                dataType: "json"
            }).success(function () {
                showLogin();
            }).fail(function (e) {
                form.showError("Validation Error");
            });
        }

        function render() {
            $loginRoot.addClass("register-pg");
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


