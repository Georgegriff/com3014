/* global define: true, document: true */
define(['underscore', 'jquery', 'text!js/plugins/swiper/template/template.html'],
        function (_, $, Template) {
            "use strict";
            return {
                init: function (app) {
                    function onRemove() {
                        app.swipers.shift();
                        var swiper = new Swiper(app.SWIPE_PRELOADS - 1),
                                $swiper = swiper.render();
                        _.each(app.swipers, function (update) {
                            var pos = update.getPosition() -1;
                            update.updatePosition(pos);
                        });
                        app.swipers.push(swiper);
                        app.$swipeCont.prepend($swiper);
                        swiper.init();

                    }
                    function Swiper(position) {
                        var html = app.parseTemplate(Template, {position: position}),
                                pos = position,
                                TRANSLATE_BY = 5,
                                $swiper = $(html);
                        function render() {
                            return $swiper;
                        }
                        function setContent(data) {

                        }
                        function updatePosition(p) {
                            pos = p;
                            var translate = TRANSLATE_BY * pos;
                            $swiper.find(".swipe-container").css({transform: 'translate(' + translate + 'px, ' + translate + 'px)'});
                            $swiper.attr("data-position", pos);

                        }
                        function yes() {
                            $swiper.find('#swipe-yes').click(function () {
                                $swiper.addClass("swipe-anim-right").delay(500)
                                        .queue(function () {
                                            $(this).remove();
                                            onRemove();
                                        });

                            });

                        }
                        function no() {
                            $swiper.find('#swipe-no').click(function () {
                                $swiper.addClass("swipe-anim-left").delay(500)
                                        .queue(function () {
                                            $(this).remove();
                                            onRemove();
                                        });
                            });
                        }
                        function getPosition(){
                            return pos;
                        }
                        function init() {

                            updatePosition(pos);
                            yes();
                            no();
                        }
                        return {
                            render: render,
                            init: init,
                            updatePosition: updatePosition,
                            getPosition: getPosition,
                            setContent: setContent
                        };
                    }
                    for (var i = 0; i < app.SWIPE_PRELOADS; i++) {
                        app.swipers.push(new Swiper(i));
                    }

                }

            }


        });
