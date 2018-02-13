'use strict';

angular.module('cityEscapeApp').controller('DestinationController',
    ['DestinationService', '$scope', function (DestinationService, $scope) {

        var self = this;
        self.destinations = [];

        self.getAllDestinations = getAllDestinations;
        self.findFilteredDestinations = findFilteredDestinations;

        $scope.holidayLengthModel = '';

        $scope.tripCategoryModel = {
            OUTDOOR_ACTIVITIES: false,
            HIKING: false,
            CYCLING: false,
            MUSIC_CONCERTS: false,
            THEATRE: false,
            MUSEUMS: false,
            ARCHITECTURE: false,
            RESTAURANTS: false,
            SHOPPING: false,
            BEACHES: false,
            NIGHT_LIFE: false,
            KIDS_FAMILY_ACTIVITIES: false,
            WALK: false,
            SPORT: false
        };

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function getAllDestinations() {
            return DestinationService.getAllDestinations();
        }

        function findFilteredDestinations(tags, holidayLength) {
            return DestinationService.findFilteredDestinations(tags, holidayLength);
        }

        $scope.$watchCollection('tripCategoryModel', function () {
            var tags = getAllTags();
            var holidayLength = $scope.holidayLengthModel;
            findFilteredDestinations(tags, holidayLength);
        });

        $scope.$watchCollection('holidayLengthModel', function () {
            var tags = getAllTags();
            var holidayLength = $scope.holidayLengthModel;
            findFilteredDestinations(tags, holidayLength);
        });

        function getAllTags() {
            var tags = "";

            angular.forEach($scope.tripCategoryModel, function (value, key) {
                if (value) {
                    tags = tags + key + ",";
                }
            });

            return tags;
        }
    }
    ]);