/* global define: true, document: true */
define(['underscore', 'jquery', 'jquery.ui', 'text!js/plugins/sidemenu/template/template.html'],
        function (_, $, $UI, Template) {
            "use strict";
            return {
                init: function (app) {
                    var menu = new Menu();
                    app.banner.attachMenu(menu);
                    
                    menu.addItem({
                       text : "Menu Item 1",
                       onClick : function(){
                           alert("Clicked 1");
                       }
                    });
                     menu.addItem({
                       text : "Menu Item 2",
                       onClick : function(){
                           alert("Clicked 2");
                       }
                    });
                     menu.addItem({
                       text : "Menu Item 3",
                       onClick : function(){
                           alert("Clicked 3");
                       }
                    });
                    function Menu() {
                        var html = app.parseTemplate(Template),
                                $sideMenu = $(html),
                                $menuItems = $sideMenu.find('.menu-items'),
                                visible = false;
                        hideListener();
                        function render() {
                            $sideMenu.prepend('<span class="menu-push"/>');
                            return $sideMenu;
                        }

                        function hideListener() {
                            $('#site-header, #content').mouseup(function (e) {
                                if (visible) {
                                    var $menu = $("#sidemenu"),
                                            $btn = $("#header-menu-btn");

                                    if ((!$menu.is(e.target) && $menu.has(e.target).length === 0) && (!$btn.is(e.target) && $btn.has(e.target).length === 0)) {
                                        hide();
                                    }
                                }
                            });
                        }
                        function show() {
                            $sideMenu.toggle("slide", {direction: "right"});
                            visible = true;
                        }
                        function hide() {
                            if (visible) {
                                $sideMenu.toggle("slide", {direction: "right"});
                                visible = false;
                            }
                        }
                        function isShown() {
                            return visible;
                        }
                        /**
                         * Add Item to Menu
                         * @param {Object} item
                         */
                        function addItem(item) {
                            var $item = $('<li class="menu-item"></li>');
                            if (item) {
                                $item.html(item.text || '');
                                $menuItems.append($item);
                                if (_.isFunction(item.onClick)) {
                                    $item.click(item.onClick);
                                }
                            }
                        }
                        return {
                            render: render,
                            show: show,
                            isShown: isShown,
                            addItem: addItem,
                            hide: hide
                        };
                    }

                }
            }


        });
