/* global define: true, document: true */
define(['underscore', 'jquery', 'jquery.ui', 'text!js/plugins/sidemenu/template/template.htm', 'js/models/user'],
        function (_, $, $UI, Template, User) {
            "use strict";
            return {
                init: function (app) {

                    var menu = new Menu();
                    app.banner.attachMenu(menu);
                    appendLoginButton();
                    menu.addItem({
                        text: app.currentUser.name,
                        href: User.getProfile(app.currentUser.userId)
                    });
                    menu.addItem({
                        text: "My Projects",
                        href: User.getProjects(app.currentUser.userId)
                    });
                    menu.addItem({
                        text: "Matched Projects",
                        onClick: function () {
                            alert("Todo");
                        }
                    });

                    function appendLoginButton() {
                        var $logoutForm = $('#logout-form');
                        $('#logout-area').append($logoutForm);
                        $logoutForm.show();

                    }

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
                            $('.slideable').addClass('animate-cont');
                            $sideMenu.toggle("slide", {direction: "right"});


                            visible = true;
                        }
                        function hide() {
                            if (visible) {

                                $sideMenu.toggle("slide", {direction: "right"}, 600);
                                $('.slideable').removeClass('animate-cont');
                                visible = false;
                            }
                        }
                        function isShown() {
                            return visible;
                        }
                        /**
                         * Add Item to Menu
                         * @param {Object} item
                         * @param {String} href
                         */
                        function addItem(item) {
                            var $item = $('<a href=""><li class="menu-item"></li></a>');
                            if (item) {
                                $item.find("li").html(item.text || '');
                                $menuItems.append($item);
                                if (item.href) {
                                    $item.prop("href", item.href);
                                }
                                if (_.isFunction(item.onClick)) {
                                    $item.click(item.onClick);
                                }
                                $item.click(function () {
                                    hide();
                                });
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
            };


        });
