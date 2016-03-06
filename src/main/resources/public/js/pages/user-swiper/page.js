/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/pages/user-swiper/template/template.html'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app, pageName) {

                    function Page() {
                        var $page = $(app.parseTemplate(Template));

                        function render() {
                            if (app.swipers) {   
                                app.swipers.attachTo($page);
                                app.swipers.showSwipers();
                                
                            }
                        }

                        function show() {
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