angular.module('frontend').controller('RegisterCtrl', function($scope,$http) {
    $scope.register = function() {
        $http({
            url: 'http://localhost:9090/register',
            method: "POST",
            params: {
                'nick': $scope.register.nick,
                'password': $scope.register.password
            },
            headers: {
                'Content-Type': 'application/json'
            }
        }).
        then(function(response) {
            var obj = JSON.parse(response.data.reply);
            $scope.registerResponse = obj;
            //alert($scope.loginResponse);
            //$state.go('home');
        }, function(response) {
            //alert("Some Error happened");
            $scope.registerResponse = "Some Error happened";
        });
    };

});
