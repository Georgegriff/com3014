/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/plugins/profile/template/template.htm'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app) {

                    function Profile(options) {
                        options = options || {};
                        var $profile = $(app.parseTemplate(Template, {
                            name: options.name,
                            image: options.image || 'img/default-profile.svg',
                            contact: options.contact,
                            description: options.description
                        }));

                        function attachTo($parent) {
                            $parent.prepend($profile);
                        }
                        function addList(listTitle, items, itemHandler) {
                            var $pContainer = $profile.find('.profile-page-info'),
                                    $plist = $('<div class="profile-list-cont">'),
                                    $listTitle = $('<h4 class="profile-list">' + listTitle + "</h4>"),
                                    $list = $('<ul class="profile-list">');
                            $pContainer.append($plist);
                            $plist.append($listTitle);
                            $plist.append($list);
                            if (_.isArray(items)) {
                                items.forEach(function (item) {
                                    if (_.isFunction(itemHandler)) {
                                        $list.append('<li>' + itemHandler(item) + '</li>');
                                    } else {
                                        $list.append('<li>' + item + '</li>');
                                    }

                                });
                            }

                        }
                        function addDescription(text) {
                            var $desc = $('<div class="profile-desc">'),
                                    $pContainer = $profile.find('.profile-page-info');
                            $pContainer.append($desc);
                            $desc.html(text);

                        }
                        return {
                            attachTo: attachTo,
                            addDescription: addDescription,
                            addList: addList

                        };
                    }

                    return Profile;
                }
            };
        });