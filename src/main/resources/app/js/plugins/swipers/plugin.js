/* global define: true, document: true */
define(['underscore', 'jquery', 'js/plugins/swipers/swiper', 'text!js/plugins/swipers/template/template.htm'], function (_, $, Swiper, Template) {
    "use strict";
    return  {
        init: function (app) {

            function Swipers() {
                var self = this;
                self.swipers = [];
                self.$swipeCont = $('<article class="swipe-stack">');

                self.SWIPE_PRELOADS = 3;

                for (var i = 0; i < self.SWIPE_PRELOADS; i++) {
                    var template = app.parseTemplate(Template, {position: i});
                    self.swipers.push(new Swiper(i, template, onRemove));
                }
                function attachTo($parent) {
                    $parent.append(self.$swipeCont);
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
                    swiper.init();

                }
                function showSwipers() {
                    var $swiper;
                    if (self.swipers && self.swipers.length) {
                        self.swipers.forEach(function attachSwiper(swiper) {
                            $swiper = swiper.render();
                            self.$swipeCont.prepend($swiper);
                            swiper.init();
                        }
                        );
                    }


                }
                function loadData() {
                    // load the data into the swipers
                }
                return {
                    attachTo: attachTo,
                    showSwipers: showSwipers,
                    loadData: loadData
                };
            }

            return new Swipers();
        }
    };


});
