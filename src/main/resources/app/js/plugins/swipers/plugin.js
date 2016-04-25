/* global define: true, document: true */
define(['underscore', 'jquery', 'js/plugins/swipers/swiper', 'text!js/plugins/swipers/template/template.htm'], function (_, $, Swiper, Template) {
    "use strict";
    return  {
        init: function (app) {

            function createNoMatchMessage() {
                var message = "No suggestions left...",
                        direction = "Please check back later.",
                        $element = $('<div class=no-matches>'),
                        $msg = $('<h1 class="no-match-msg">' + message + '</h1>'),
                        $direction = $('<i class="rotTfive fa fa-frown-o"></i><h2 class="no-match-dir">' + direction + '</h2>');

                $element.append($msg);
                $element.append($direction);
                return $element;

            }

            function Swipers() {
                var self = this,
                        $noMatchesMessage = createNoMatchMessage();

                self.swipers = [];
                self.$swipeCont = $('<article class="swipe-stack">');

                self.SWIPE_PRELOADS = 3;
                for (var i = 0; i < self.SWIPE_PRELOADS; i++) {
                    var template = app.parseTemplate(Template, {position: i});
                    self.swipers.push(new Swiper(i, template, onRemove));
                }
                function attachTo($parent) {
                    self.$swipeCont.hide();
                    $noMatchesMessage.hide();
                    $parent.append(self.$swipeCont);
                    $('#content').append($noMatchesMessage);
                }
                function onRemove() {
                    self.swipers.shift();

                    var position = self.SWIPE_PRELOADS - 1,
                            template = app.parseTemplate(Template, {position: position}),
                            swiper = new Swiper(position, template, onRemove),
                            $swiper = swiper.render();
                    _.each(self.swipers, function (update) {
                        var pos = update.getPosition() - 1;
                        update.updatePosition(pos);
                    });
                    self.swipers.push(swiper);
                    self.$swipeCont.prepend($swiper);
                    self.loadAction(swiper);

                }
                function showSwipers() {
                    var $swiper;
                    $noMatchesMessage.fadeOut();
                    if (self.swipers && self.swipers.length) {
                        self.swipers.forEach(function attachSwiper(swiper) {
                            $swiper = swiper.render();
                            self.$swipeCont.prepend($swiper);
                            swiper.init();
                            if (self.loadAction) {
                                self.loadAction(swiper);
                            }
                        }
                        );
                    }
                }
                function onAccept(action) {
                    if (_.isFunction(action)) {
                        self.onAccept = action;
                    }
                }
                function onReject(action) {
                    if (_.isFunction(action)) {
                        self.onReject = action;
                    }
                }
                function onDataLoad(action) {
                    if (_.isFunction(action)) {
                        self.loadAction = action;
                    }
                }
                function hide() {
                    $noMatchesMessage.css("display", "flex")
                            .hide()
                            .fadeIn();
                    self.$swipeCont.fadeOut();
                }
                function show() {
                    $noMatchesMessage
                            .fadeOut();
                    self.$swipeCont.fadeIn();
                }
                function pending() {
                    var message = "Loading Suggestions...",
                            $element = $('<div class="no-matches">'),
                            $msg = $('<h1 class="no-match-msg">' + message + '</h1>'),
                            $direction = $('<i class="spin fa fa-refresh"></i>');
                    $element.append($msg);
                    $element.append($direction);
                    return $element;
                }
                return {
                    attachTo: attachTo,
                    showSwipers: showSwipers,
                    show: show,
                    onDataLoad: onDataLoad,
                    onAccept: onAccept,
                    onReject: onReject,
                    pending: pending,
                    hide: hide
                };
            }

            return new Swipers();
        }
    };


});
