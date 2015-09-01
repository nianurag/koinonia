angular.module('frontend').controller('HomeCtrl', function($scope, $http) {

    var baseURL = 'http://localhost:9090/';

    $http.get(baseURL).
    then(function(response) {
        $scope.getresponse = response.data.reply;
    }, function(response) {
        console.log("Some error happened");
    });

    $http.post(baseURL).
    then(function(response) {
        $scope.postresponse = response.data.reply;
    }, function(response) {
        console.log("Some error happened");
    });

});
