var app = angular.module('cityEscapeApp',['ui.router','ngStorage','ui.bootstrap']);

app.constant('urls', {
    BASE: 'http://localhost:8090/cityescape',
    DESTINATIONS_API : 'http://localhost:8090/cityescape/destinations/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/destinations',
                controller:'DestinationController',
                controllerAs:'ctrl',
                resolve: {
                    users: function ($q, DestinationService) {
                        console.log('Load all destinations');
                        var deferred = $q.defer();
                        DestinationService.loadAllDestinations().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

