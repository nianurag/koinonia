angular.module('frontend').controller('LoginCtrl', function($scope, $http, $state) {
    $scope.login = function() {
        $http({
            url: 'http://localhost:9090/login',
            method: "POST",
            params: {
                'nick': $scope.login.nick,
                'password': $scope.login.password
            },
            headers: {
                'Content-Type': 'application/json'
            }
        }).
        then(function(response) {
            var obj = JSON.parse(response.data.reply);
            $scope.loginResponse = obj;
            console.log($scope.loginResponse);
            if ($scope.loginResponse === "Success") {
                $state.go('messages');
                console.log(typeof($scope.loginResponse));
                //$scope.loginResponse = "Welcome, ";
            } else {
                $scope.loginResponse = "Oops, please recheck";
            }

            //alert($scope.loginResponse);

        }, function(response) {
            //alert("Some Error happened");
            $scope.loginResponse = "Oops, please recheck";
        });
    };

});
