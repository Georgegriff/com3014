/* global define: true, document: true */
define(['underscore', 'jquery'],
        function (_, $) {
            "use strict";

            function Swiper(position, html, onRemove, defaultImagePath) {
                var pos = position,
                        TRANSLATE_BY = 5,
                        $swiper = $(html),
                        $swiperProfileInfo;
                function render() {
                    return $swiper;
                }
                function setContent(data) {

                }
                function switchImage(path) {
                    $swiper.find('.swiper-img').prop("src", path);
                }
                function setTitle(text) {
                    $swiperProfileInfo.find('.profile-title').html(text);
                }
                function addList(listTitle, items, itemHandler) {
                    var $pContainer = $swiperProfileInfo.find('.profile-cont'),
                            $plist = $('<div class="profile-list-cont">'),
                            $listTitle = $('<h4 class="profile-list">' + listTitle + "</h4>"),
                            $list = $('<ul class="profile-list">');
                    $pContainer.append($plist);
                    $plist.append($listTitle);
                    $plist.append($list);
                    if(_.isArray(items)){
                        items.forEach(function(item){
                            if(_.isFunction(itemHandler)){
                                   $list.append('<li>' + itemHandler(item) + '</li>'); 
                            }else {
                                   $list.append('<li>' +item + '</li>'); 
                            }
                        
                        });
                    }
                    
                }
                function addDescription(text) {
                    var $desc = $('<div class="profile-desc">'),
                            $pContainer = $swiperProfileInfo.find('.profile-cont');
                    $pContainer.append($desc);
                    $desc.html(text);

                }
                function setSubtitle(text) {
                    $swiperProfileInfo.find('.profile-subtitle').html(text);
                }
                function setRightSubtitle(text) {
                    $swiperProfileInfo.find('.profile-right-subtitle').html(text);
                }
                function updatePosition(p) {
                    pos = p;
                    var translate = TRANSLATE_BY * pos;
                    $swiper.find(".swipe-container").css({transform: 'translate(' + translate + 'px, ' + translate + 'px)'});
                    $swiper.attr("data-position", pos);

                }
                function yes(action) {
                    $swiper.find('.swipe-yes').click(function () {
                        if (_.isFunction(action)) {
                            action();
                        }
                        $swiper.addClass("swipe-anim-right").delay(500)
                                .queue(function () {
                                    $(this).remove();
                                    onRemove();
                                });

                    });
                }
                function no(action) {
                    $swiper.find('.swipe-no').click(function () {
                        if (_.isFunction(action)) {
                            action();
                        }
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
                function init(options) {
                    options = options || {};
                    if (defaultImagePath) {
                        switchImage(defaultImagePath);
                    }
                    updatePosition(pos);
                    yes(options.onAccept);
                    no(options.onReject);
                    $swiperProfileInfo = $swiper.find('.profile-info');
                }
                return {
                    render: render,
                    init: init,
                    setImage: switchImage,
                    updatePosition: updatePosition,
                    getPosition: getPosition,
                    setContent: setContent,
                    setTitle: setTitle,
                    setSubtitle: setSubtitle,
                    addDescription: addDescription,
                    setRightSubtitle: setRightSubtitle,
                    addList: addList
                };
            }

            return Swiper;

        });
