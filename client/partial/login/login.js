angular.module('frontend').controller('LoginCtrl', function($scope, $http, $state) {
    $scope.login = function() {
        window.nick = $scope.login.nick;
        window.password = $scope.login.password;

        $http({
            url: 'http://localhost:9090/login',
            method: "POST",
            params: {
                'nick': window.nick,
                'password': window.password
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
                $state.go('rememberme');
                //console.log(typeof($scope.loginResponse));
                //$scope.loginResponse = "Welcome, ";
            } else {
                $scope.loginResponse = "My friend I don't know you";
            }

            //alert($scope.loginResponse);

        }, function(response) {
            //alert("Some Error happened");
            $scope.loginResponse = "Oops, please recheck";
        });
    };

});
