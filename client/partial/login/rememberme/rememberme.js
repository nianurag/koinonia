angular.module('frontend').controller('RemembermeCtrl', function($scope, $http, $state) {

    //console.log($scope.$parent);
    //console.log(window.nick);

    $scope.nick = window.nick;
    $scope.password = window.password;
    $scope.messages = function() {
        $state.go('messages');
    };
    $scope.savesession = function() {
        $http({
            url: 'http://localhost:9090/savesession',
            method: "POST",
            params: {
                'nick': $scope.nick,
                'password': $scope.password
            },
            headers: {
                'Content-Type': 'application/json'
            }
        }).
        then(function(response) {
            $scope.savesession = response.data.reply;
            console.log($scope.savesession);
            if ($scope.savesession === "Cookie Success") {
                $state.go('messages');
            } else {
                console.log("Something went wrong");
            }
        }, function(response) {
            //alert("Some Error happened");
            console.log("Server Down maybe?");
        });
    };
});
