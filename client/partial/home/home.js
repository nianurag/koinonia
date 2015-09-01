angular.module('frontend').controller('HomeCtrl',function($scope,$http){

console.log("Home controller has been successfully loaded");

$http.get('http://localhost:9090/').
  then(function(response) {
    console.log("Hey it was successfully requested" + JSON.stringify(response.data.reply));
    $scope.response = response.data.reply;
  }, function(response) {
    console.log("Some error happened");
  });

});