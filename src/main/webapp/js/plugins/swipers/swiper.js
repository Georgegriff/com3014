/* global define: true, document: true */
define(['underscore', 'jquery'],
        function (_, $) {
            "use strict";

            function Swiper(position, html, onRemove) {
                var pos = position,
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
                function getPosition() {
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

            return Swiper;

        });
