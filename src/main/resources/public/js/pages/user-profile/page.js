/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/pages/user-profile/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app, pageName) {

                    function Page() {
                        var $page = null;

                        function render() {
                            if (app.currentUser) {

                                $page.append(JSON.stringify(app.currentUser));

                            }
                        }

                        function show() {
                            $page = $(app.parseTemplate(Template));
                            app.container.showContent($page);
                            render();
                        }

                        return {
                            show: show
                        };
                    }
                    app.pages[pageName] = new Page();
                }
            };
        });