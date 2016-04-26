/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/plugins/form/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app) {
                    var validators = [];
                    function SiteForm(options) {
                        var $form = $(app.parseTemplate(Template, {
                            title: options.title,
                            name: options.name,
                            description: options.description
                        }));
                        options = options || {};
                        function addField(options) {
                            options = options || {};
                            var fieldType = options.type || "text",
                                    fieldLabel = options.label || "",
                                    $field = $('<span class="form-field"><input placeholder="' + fieldLabel + '" type="' + fieldType + '"/></span>"');

                            if (options.id) {
                                $field.attr("id", options.id);
                            }

                            if (options.subElement) {
                                $(options.subElement).append($field);
                            } else {
                                $form.find("form .form-fields").append($field);
                            }

                            if (_.isFunction(options.validator)) {
                                validators.push(function () {
                                    return options.validator($field.find("input").val());
                                });
                            }

                        }
                        function addComboAndField(options) {
                            options = options || {};
                            var fieldType = options.type || "text",
                                    fieldLabel = options.label || "",
                                    $field = $('<span class="form-field"><input placeholder="' + fieldLabel + '" type="' + fieldType + '"/></span>"'),
                                    $select = $('<span class="dropdown small-drop"><select name=""></select></span>');
                            $field.prepend($select);

                            if (options.id) {
                                $field.attr("id", options.id);
                            }

                            if (options.subElement) {
                                $(options.subElement).append($field);
                            } else {
                                $form.find("form .form-fields").append($field);
                            }
                            if (_.isFunction(options.validator)) {
                                validators.push(function () {
                                    return options.validator($field.find("input").val());
                                });
                            }
                        }
                        function checkValidation() {
                            var isValid = true;
                            validators.forEach(function (validator) {
                                isValid = isValid && validator();
                            });
                            return isValid;
                        }
                        function addButton(options) {
                            options = options || {};
                            var fieldLabel = options.label || "",
                                    $button = $('<div class="form-item btn"><button class="core-button form-btn" type="submit">' + fieldLabel + '</button></div>');
                            $form.find("form").append($button);
                            
                            if (options.id) {
                                $button.attr("id", options.id);
                            }

                            if (_.isFunction(options.action)) {
                                $button.click(function (e) {
                                    e.preventDefault();
                                    if (checkValidation()) {
                                        return options.action(e);
                                    } else {
                                        showError("Validation Error");

                                    }

                                });
                            }

                        }
                        function additionButton(options) {
                            options = options || {};
                            var fieldLabel = options.label || "",
                                    $field = $('<div class="addition-field"><a>+ </a><span>' + fieldLabel + '</span></div>"');
                            $form.find("form .form-fields").append($field);
                            
                            if (options.id) {
                                $field.attr("id", options.id);
                            }
                            
                            if (_.isFunction(options.validator)) {
                                validators.push(function () {
                                    return options.validator($field.find("input").val());
                                });
                            }
                            $field.find('a').click(function (e) {
                                if (_.isFunction(options.action)) {
                                    options.action(e);
                                }
                            });
                        }
                        function showError(err) {
                            var $error = $form.find(".form-error");
                            $error.html(err);
                            $error.show();
                        }

                        function attachTo($parent) {
                            $parent.prepend($form);
                        }
                        return {
                            addField: addField,
                            addButton: addButton,
                            attachTo: attachTo,
                            addComboAndField: addComboAndField,
                            additionButton: additionButton,
                            showError : showError
                        };
                    }
                    return SiteForm;
                }
            };
        });