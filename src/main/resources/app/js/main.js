/*global require:true*/
require.config({
    baseUrl: '../',
    waitSeconds: 60,
    paths: {
        jquery: 'js/vendor/jquery.min',
        underscore: 'js/vendor/underscore-min',
        text: 'js/vendor/text',
        'jquery.ui': 'js/vendor/jquery-ui.min'
    }

});
require.onError = function (err) {
    if (err.requireType === 'timeout') {
        console.error(err);
    } 
    else {
       console.error(err);
    }   
};    
require(['jquery', 'js/app'], function ($, app) {
    "use strict";

    $(document).ready(function () {
        app.init();
    });
});


