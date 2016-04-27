/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/pages/create-project/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app, pageName) {
                    var self = this;
                    function Page() {


                        function render() {
                            var $page = $(app.parseTemplate(Template, {
                                projectsLink: app.models.project.getUserProjectsPath(app.currentUser.userId)
                            })),
                                $pageCont = $page.find('#project-form');
                            app.container.showContent($page);
                            self.createForm(app, $pageCont);
                        }
                        function show() {
                            render();
                        }
                        return {
                            show: show
                        };
                    }
                    app.pages[pageName] = new Page();
                }, createForm: function createForm(app, $parent, isEdit) {
                    var form = new app.plugins.SiteForm({
                        title: "",
                        name: "createProjectForm",
                        description: 'Create A New Project'
                    }),
                            buttonText = isEdit ? "Update" : "Create";
                    form.attachTo($parent);
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
                            $location = form.addField({label: "Post Code", validator: function (value) {
                                    return value && value !== "";
                                }}),
                            $qualifications = form.additionButton({id: "add-qual", label: "Add Qualification", action: function (e) {
                                    form.addComboAndField({subElement: "#add-qual", validator: function (value) {
                                            return value && value !== "";
                                        }});
                                }}),
                            $skills = form.additionButton({id: "add-skill", label: "Add Skill", action: function (e) {
                                    form.addComboAndField({subElement: "#add-skill", validator: function (value) {
                                            return value && value !== "";
                                        }});
                                }}),
                            $interests = form.additionButton({id: "add-inter", label: "Add Interest", action: function (e) {
                                    form.addField({subElement: "#add-inter", label: "Interest", validator: function (value) {
                                            return value && value !== "";
                                        }});
                                }});
                    form.addButton({label: buttonText, action: function (e) {
                            e.preventDefault();
                            registerUser({
                                $firstName: $firstName,
                                $lastName: $lastName,
                                $email: $email,
                                $username: $username,
                                $location: $location,
                                $qualifications: $qualifications,
                                $$skills: $skills,
                                $interests: $interests
                            });
                        }});
                }
            };
        });