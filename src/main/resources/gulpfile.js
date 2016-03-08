var gulp = require('gulp'),
        amdOptimize = require("amd-optimize"),
        uglify = require("gulp-uglify"),
        concat = require("gulp-concat");
gulp.task('scripts', function () {
    return gulp.src("public/js/**/*.js")
            .pipe(amdOptimize("js/main", {
                baseUrl: 'public/',
                paths: {
                    jquery: 'js/vendor/jquery.min',
                    'jquery.ui': 'js/vendor/jquery-ui.min',
                    "underscore": 'js/vendor/underscore-min',
                    text: 'js/vendor/text'
                }
            }))
                    .pipe(concat("main.js"))
            .pipe(uglify())
            .pipe(gulp.dest('public/dist/js'))
});
gulp.task('copyjson', function () {
    return gulp.src('public/js/**/*.{json}')
            .pipe(gulp.dest('public/dist/js'));
});
gulp.task('build', [
    'scripts'
]);