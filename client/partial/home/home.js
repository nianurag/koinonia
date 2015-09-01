angular.module('frontend').controller('HomeCtrl',function($scope,$http){

console.log("Home controller has been successfully loaded");

// Simple GET request example :
$http.get('http://localhost:9090/').
  then(function(response) {
    // this callback will be called asynchronously
    // when the response is available
    console.log("Hey it was successfully requested" + JSON.stringify(response));
  }, function(response) {
    // called asynchronously if an error occurs
    // or server returns response with an error status.
    console.log("Some error happened");
  });

});