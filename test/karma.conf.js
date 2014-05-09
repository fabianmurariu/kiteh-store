module.exports = function(config){
    config.set({

        basePath : '../',

        files : [
            'src/main/resources/public/components/angular/angular.js',
            'src/main/resources/public/components/angular-resource/angular-resource.js',
            'src/main/resources/public/components/angular-route/angular-route.js',
            'src/main/resources/public/components/angular-mocks/angular-mocks.js',
            'src/main/resources/public/components/restangular/dist/restangular.min.js',
            'src/main/resources/public/components/lodash/dist/lodash.compat.min.js',
            'src/main/resources/public/components/ngDialog/js/ngDialog.min.js',
            'src/main/resources/public/components/app/js/**/*.js',
            'test/unit/**/*.js'
        ],

        autoWatch : true,

        frameworks: ['jasmine'],

        browsers : ['Chrome'],

        plugins : [
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-jasmine'
        ],

        junitReporter : {
            outputFile: 'test_out/unit.xml',
            suite: 'unit'
        }

    });
};