angular.module('backstretch', [])
	.directive('backstretch', function () {
		return {
			restrict: 'A',                      
			link: function (scope, element, attr) {
                            scope.$watch('bgs', function () {
                            element.backstretch(attr.bgs.split(','), {duration: 1000, fade: 4000});
                        });
                            
                            
                            
			}
		}
	});
