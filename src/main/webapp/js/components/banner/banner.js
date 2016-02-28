/* global define: true */
define(['jquery'],
        function ($) {
            "use strict";
            return {
                init: function (app) {
                    function Banner() {
                        var $menuButton = $('#header-menu-btn'),
                                $content = $('#content'),
                                bannerMenu = null;
                        function attachMenu(menu) {
                            bannerMenu = menu;
                            menu.render().insertAfter($content);
                            $menuButton.click(function () {
                                if (!menu.isShown()) {
                                    menu.show();
                                } else {
                                    menu.hide();
                                }
                            });
                        }
                        function getMenu() {
                            return bannerMenu;
                        }
                        return {
                            attachMenu: attachMenu,
                            getMenu: getMenu
                        };
                    }
                    app.banner = new Banner();
                }
            }


        });
