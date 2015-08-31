angular.module('frontend', ['ui.bootstrap','ui.utils','ui.router','ngAnimate']);

angular.module('frontend').config(function($stateProvider, $urlRouterProvider, $locationProvider) {

    /*$locationProvider.html5Mode(true);*/

    $stateProvider.state('register', {
        url: '/register',
        templateUrl: 'partial/register/register.html'
    });
    $stateProvider.state('login', {
        url: '/login',
        templateUrl: 'partial/login/login.html'
    });
    $stateProvider.state('messages', {
        url: '/messages',
        templateUrl: 'partial/messages/messages.html'
    });
    $stateProvider.state('home', {
        url: '/',
        templateUrl: 'partial/home/home.html'
    });
    /* Add New States Above */
    $urlRouterProvider.otherwise('/');

});

angular.module('frontend').run(function($rootScope) {

    $rootScope.safeApply = function(fn) {
        var phase = $rootScope.$$phase;
        if (phase === '$apply' || phase === '$digest') {
            if (fn && (typeof(fn) === 'function')) {
                fn();
            }
        } else {
            this.$apply(fn);
        }
    };

});
