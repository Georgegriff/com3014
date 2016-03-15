var directories = {
    SRC_DIR: "./",
    TARGET_DIR: "../../../target/classes/"
},
gulp = require('gulp'),
        amdOptimize = require("amd-optimize"),
        uglify = require("gulp-uglify"),
        concat = require("gulp-concat"),
        concatCss = require('gulp-concat-css'),
        autoprefixer = require('gulp-autoprefixer');
gulp.task('optimise-scripts', function () {
    return gulp.src("app/js/**/*.js")
            .pipe(amdOptimize("js/main", {
                baseUrl: 'app/',
                paths: {
                    jquery: 'js/vendor/jquery.min',
                    'jquery.ui': 'js/vendor/jquery-ui.min',
                    "underscore": 'js/vendor/underscore-min',
                    text: 'js/vendor/text'
                }
            }))
            .pipe(concat("main.js"))
            .pipe(uglify())
            .pipe(gulp.dest('public/js'));
});
gulp.task('copy-deps-js', function(){
      return gulp.src('app/js/vendor/{require,text}.js')
            .pipe(gulp.dest('public/js/vendor'));
});
gulp.task('copyjson', function () {
    return gulp.src('app/js/**/**/*.json')
            .pipe(gulp.dest('public/js'));
});

gulp.task('copy-fonts', function () {
    return gulp.src("app/css/fonts/**/*.*")
            .pipe(gulp.dest("public/css/fonts"));
});
gulp.task('copy-plugins', function () {
    return gulp.src("app/js/plugins/**/*.*")
            .pipe(gulp.dest("public/js/plugins"));
});
gulp.task('copy-pages', function () {
    return gulp.src("app/js/pages/**/*.*")
            .pipe(gulp.dest("public/js/pages"));
});
gulp.task('copy-css', function () {
    return gulp.src("app/css/**/*.css")
            .pipe(autoprefixer({
                browsers: ['last 2 versions'],
                cascade: false
            }))
            .pipe(gulp.dest('public/css'));
});
gulp.task('copy-img', function () {
    return gulp.src("app/img/**/*.*")
            .pipe(gulp.dest(directories.SRC_DIR + "public/img"));
});
gulp.task('copy-templates', function () {
    return gulp.src("templates/*.html")
            .pipe(gulp.dest(directories.SRC_DIR + "templates"));
});
gulp.task('build', [
    'copy-deps-js',
    'copy-fonts',
    'optimise-scripts',
    'copy-css',
    'copy-img',
    'copy-templates',
    'copyjson',
    'copy-pages',
    'copy-plugins'
]);


function error(err){
     console.log(err.toString());
    this.emit('end');
}
gulp.task('watch', function () {
    gulp.watch('app/js/**/**/*.*').on('change', function () {
        console.log("Building JavaScript..");
        return gulp.src("app/js/**/**/*.*")
                .pipe(gulp.dest(directories.TARGET_DIR + "public/js"));
    });
    gulp.watch('app/css/**/*.*').on('change', function () {
        console.log("Building CSS..");
        return gulp.src("app/css/**/*.css")
                .pipe(autoprefixer({
                    browsers: ['last 2 versions'],
                    cascade: false
                }))
                   .on('error',error)
                  .pipe(gulp.dest(directories.TARGET_DIR + 'public/css'));
    });
    gulp.watch('app/img/**/*.*').on('change', function () {
        console.log("(Building Images..");
        return gulp.src("app/img/**/*.*")
                .pipe(gulp.dest(directories.TARGET_DIR + "public/img"));
    });
    gulp.watch('templates/*.html').on('change', function () {
        console.log("Building Templates...");
        return gulp.src("templates/*.html")
                .pipe(gulp.dest(directories.TARGET_DIR + "templates"));

    });
});