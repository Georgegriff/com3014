/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/pages/user-swiper/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app, pageName) {

                    function Page() {
                        var $page = $(app.parseTemplate(Template));

                        function render() {
                            var swipers =  app.plugins.swipers;
                            if (swipers) {   
                                swipers.attachTo($page);
                                swipers.showSwipers();
                                  app.plugins.swipers.show();
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