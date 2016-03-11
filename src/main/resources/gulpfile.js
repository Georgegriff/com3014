var TARGET_DIR = "../../../target/classes/",
        gulp = require('gulp'),
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
gulp.task('watch', function () {
    gulp.watch('public/js/**/*.*').on('change', function () {
         console.log("JavaScript Changed");
          return gulp.src("public/js/**/*.*")
                .pipe(gulp.dest(TARGET_DIR + "public/js"));
    });
    gulp.watch('public/css/**/*.*').on('change', function () {
         console.log("CSS Changed");
          return gulp.src("public/css/**/*.*")
                .pipe(gulp.dest(TARGET_DIR + "public/css"));
    });
    gulp.watch('public/img/**/*.*').on('change', function () {
         console.log("(IMG Changed");
          return gulp.src("public/img/**/*.*")
                .pipe(gulp.dest(TARGET_DIR + "public/img"));
    });
    gulp.watch('templates/*.html').on('change', function () {
        console.log("Template Changed");
        return gulp.src("templates/*.html")
                .pipe(gulp.dest(TARGET_DIR + "templates"));

    });
});