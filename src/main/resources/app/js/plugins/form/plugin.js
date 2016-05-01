/* global define: true, document: true */

/**
 * SiteForm plugin module
 */
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
                            return $field;

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
                            return $field;
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
                            return $button;

                        }

                        function populateCombo($select, list, valueAndNameExtractor) {
                            if (list && list.length) {
                                list.forEach(function (item) {
                                    var inputValues = valueAndNameExtractor(item);
                                    $select
                                            .append($("<option></option>")
                                                    .attr("value", inputValues.value)
                                                    .text(inputValues.name));
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
                            return $field;
                        }

                        function getComboFieldValues($wrapper) {
                            var $fields = $wrapper.find(".form-field"),
                                    values = [];
                            if ($fields && $fields.length) {
                                $fields.each(function () {
                                    values.push({
                                        inputValue: $(this).find("input").val(),
                                        comboValue: comboValue($(this).find("select"))
                                    });
                                });
                            }
                            return values;

                        }

                        function comboValue($select) {
                            if ($select && $select.length) {
                                var select = $select.get(0),
                                        selectedOption = select.options[select.selectedIndex];
                                return {
                                    value: selectedOption.value,
                                    text: selectedOption.text
                                };
                            } else {
                                return {
                                    value: "",
                                    text: ""
                                };
                            }

                        }
                        
                        function getFieldValues($wrapper){
                             var $fields = $wrapper.find(".form-field"),
                                    values = [];
                            if ($fields && $fields.length) {
                                $fields.each(function () {
                                    values.push($(this).find("input").val());
                                });
                            }
                            return values;
                        }
                        
                        function getComboValues($wrapper) {
                            var $fields = $wrapper.find(".form-field"),
                                    values = [];
                            if ($fields && $fields.length) {
                                $fields.each(function () {
                                    values.push(comboValue($(this).find("select")));
                                });
                            }
                            return values;
                        }

                        function showError(err) {
                            var $error = $form.find(".form-error");
                            $error.html(err);
                            $error.show();
                        }
                          function showDetailedError(err) {
                            var $error = $form.find(".detailed-error");
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
                            getComboFieldValues: getComboFieldValues,
                            getComboValues: getComboValues,
                            getFieldValues : getFieldValues,
                            populateCombo: populateCombo,
                            additionButton: additionButton,
                            showError: showError,
                            showDetailedError : showDetailedError
                        };
                    }
                    return SiteForm;
                }
            };
        });