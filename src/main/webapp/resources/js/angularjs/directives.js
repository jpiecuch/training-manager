 training.directive('ngUniform',function($timeout){
  return {
    restrict:'A',
    require: '?checklistModel',
    link: function(scope, element, attrs, checkList) {
        element.uniform({useID: false});
    }
  };
}).directive('ngSpinner', function() {
    return {
        restrict: 'A',
        link: function(scope, element) {
            element.numeric({negative: false});
        }
    };
}).directive("repeatPassword", function() {
    return {
        require: "ngModel",
        link: function(scope, elem, attrs, ctrl) {
            var otherInput = elem.inheritedData("$formController")[attrs.repeatPassword];
            
            ctrl.$parsers.push(function(value) {
                if(value === otherInput.$viewValue) {
                    ctrl.$setValidity("repeat", true);
                    return value;
                }
                ctrl.$setValidity("repeat", false);
            });

            otherInput.$parsers.push(function(value) {
                ctrl.$setValidity("repeat", value === ctrl.$viewValue);
                return value;
            });
        }
    };
}).directive('ngAvailability', ['$http', 'contextPath', function (async, contextPath) {
  return {
    require: 'ngModel',
    link: function (scope, elem, attrs, ctrl) {
      elem.on('blur', function (evt) {
        scope.$apply(function () {
            async({ method: 'GET', url: contextPath + '/authentication/availability/' + attrs.ngAvailability + '?value=' + elem.val()}).success(function(data) {
                ctrl.$setValidity('availability', data === 'true' ? true : false);
            });
        });
        });
      }
    }
  }
]);;

