'use strict';

angular.module('cityEscapeApp').factory('DestinationService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllDestinations: loadAllDestinations,
                getAllDestinations: getAllDestinations,
                findFilteredDestinations: findFilteredDestinations
            };

            return factory;

            function loadAllDestinations() {
                console.log('Fetching all destinations');
                var deferred = $q.defer();
                $http.get(urls.DESTINATIONS_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all destinations');
                            $localStorage.destinations = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading destinations');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllDestinations(){
                return $localStorage.destinations;
            }

            function findFilteredDestinations(tags, holidayLength){
                console.log('Fetching filtered destinations, tags= ' + tags + ", holidayLength= " + holidayLength);
                var deferred = $q.defer();
                $http.get(urls.DESTINATIONS_API + "?tags=" + tags + "&holidayLength=" + holidayLength)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all destinations');
                            $localStorage.destinations = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading destinations');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
        }
    ]);