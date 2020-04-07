var gulp           = require('gulp');
var concat         = require('gulp-concat');
var concatVendor   = require('gulp-concat-vendor');
var uglify         = require('gulp-uglify');
var minify         = require('gulp-minify-css')
var mainBowerFiles = require('main-bower-files');
var inject         = require('gulp-inject');
var runSequence    = require('gulp-run-sequence');
var gzip           = require('gulp-gzip');
var clone          = require('gulp-clone');
var series         = require('stream-series');
 
var vendorJs;
var vendorCss;
 
gulp.task('lib-js-files', function () {
    vendorJs = gulp.src(mainBowerFiles('**/*.js'),{ base: 'bower_components' })    													   
        .pipe(concatVendor('lib.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest('src/main/webapp/static/js/dest'));
 
    vendorJs.pipe(clone())
        .pipe(gzip())
        .pipe(gulp.dest('src/main/webapp/static/js/dest'));
});
 
gulp.task('lib-css-files', function () {
    vendorCss = gulp.src(mainBowerFiles('**/*.css'), {base: 'my_folder'})
        .pipe(concat('lib.min.css'))
        .pipe(minify())
        .pipe(gulp.dest('src/main/webapp/static/dest/js'));
 
    vendorCss.pipe(clone())
        .pipe(clone())
        .pipe(gzip())
        .pipe(gulp.dest('src/main/webapp/static/dest/js'));
});
 
gulp.task('index', function () {
    var target = gulp.src("src/main/webapp/index.html");
    var sources = gulp.src(['src/main/webapp/static/js/*.js', 'src/main/webapp/static/css/*.css'], {read: false});
    return target.pipe(inject(series(vendorJs, vendorCss, sources), {relative: true}))
        .pipe(gulp.dest('src/main/webapp/static/dest/js'));
});
 
gulp.task('copyFonts', function() {
    gulp.src(mainBowerFiles('**/dist/fonts/*.{ttf,woff,woff2,eof,svg}'))
        .pipe(gulp.dest('src/main/webapp/static/dest/js'));
});
 
// Default Task
gulp.task('default', function () {
    runSequence('lib-js-files');
});