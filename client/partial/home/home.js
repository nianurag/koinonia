angular.module('frontend').controller('HomeCtrl', function($scope, $http) {

    var baseURL = 'http://localhost:9090/';

/*    $scope.register = function() {

    //alert($scope.user.nick) ;
    $http.post(baseURL.concat("login?nick=").$scope.user.nick.concat("&password=").$scope.user.password).
    then(function(response){
      var obj = JSON.parse(response.data.reply);
      $scope.loginResponse = obj;
      alert($scope.loginResponse);
    }, function(response) {
      alert("Some Error happened");
    });
    };*/

      $scope.register = function () {
    $http({
      url: 'http://localhost:9090/login',
      method: "POST",
      params: {'nick': $scope.user.nick, 'password': $scope.user.password},
      headers: {'Content-Type': 'application/json'}
    }).
    then(function(response) {
      var obj = JSON.parse(response.data.reply);
      $scope.loginResponse = obj;
      alert($scope.loginResponse);
      //$state.go('home');
    }, function(response) {
      alert("Some Error happened");
    });
  }

});
