/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/plugins/form/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app) {
                    function SiteForm(options) {
                        var $form = $(app.parseTemplate(Template, {
                            title: options.formTitle,
                            name: options.formName,
                            description: options.description
                        }));
                        options = options || {};
                        function addField(options) {
                            options = options || {};
                            var fieldType = options.type || "text",
                                    fieldLabel = options.label || "",
                                    $field = $('<span class="form-field"><input type="' + fieldType + '"/><label>' + fieldLabel + "</label></span>");

                            $form.append($field);

                        }
                        function attachTo($parent) {
                            $parent.prepend($form);
                        }
                        return {
                            addField: addField,
                            attachTo: attachTo
                        }   ;
                    }
                    return SiteForm;
                }
            };
        });