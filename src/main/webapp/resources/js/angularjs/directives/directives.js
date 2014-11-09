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
]).directive('equipmentDesc', function($compile) {
     return {
         restrict: 'E',
         scope: {
             type: "=",
             equipment: "=",
             length: "="
         },
         compile: function(element, attrs) {
             return function(scope, element, attrs) {
                 var html;
                 switch (scope.type) {
                     case 'bars': {
                         html = "<div class=\"col-md-4\">{{'equipment.strength' | translate}}: <span>{{equipment.strength}}</span></div> <div class=\"col-md-4\"> {{'equipment.length' | translate}}: <span>{{equipment.lengthOf + ' ' + equipment.lengthOfUnit.shortName}}</span></div><div class=\"col-md-4\"> {{'equipment.handles' | translate}}: <span>{{equipment.handlesNo}}</span></div>";
                         break;
                     }
                     case 'benches': {
                         html = "<div class=\"col-md-4\">{{'equipment.type' | translate}}: <span>{{equipment.type}}</span></div> <div class=\"col-md-4\"> {{'equipment.length' | translate}}: <span>{{equipment.lengthOf + ' ' + equipment.lengthOfUnit.shortName}}</span></div><div class=\"col-md-4\"> {{'equipment.height' | translate}}: <span>{{equipment.height + ' ' + equipment.heightUnit.shortName}}</span></div>";
                         break;
                     }
                     case 'dumbbells': {
                         html = "<div class=\"col-md-4\">{{'equipment.weight' | translate}}: <span>{{equipment.weight + ' ' + equipment.weightUnit.shortName}}</span></div> <div class=\"col-md-8\"> {{'equipment.permanent.load' | translate}}: <span>{{equipment.connectedLoad ? 'yes' : 'no' | translate}}</span></div>";
                         break;
                     }
                     case 'loads': {
                        html = "<div class=\"col-md-4\">{{'equipment.weight' | translate}}: <span>{{equipment.weight + ' ' + equipment.weightUnit.shortName}}</span></div> <div class=\"col-md-8\"> {{'equipment.hole.diameter' | translate}}: <span>{{equipment.holeDiameter + ' ' + equipment.holeDiameterUnit.shortName}}</span></div>";
                        break;
                     }
                     case 'necks': {
                         html = "<div class=\"col-md-4\">{{'equipment.type' | translate}}: <span>{{equipment.type | translate}}</span></div><div class=\"col-md-4\">{{'equipment.weight' | translate}}: <span>{{equipment.weight + ' ' + equipment.weightUnit.shortName}}</span></div> <div class=\"col-md-4\"> {{'equipment.diameter' | translate}}: <span>{{equipment.diameter + ' ' + equipment.diameterUnit.shortName}}</span></div>";
                         break;
                     }
                     case 'press': {
                         html = "<div class=\"col-md-4\">{{'equipment.strength' | translate}}: <span>{{equipment.strength + ' ' + equipment.strengthUnit.shortName}}</span></div> <div class=\"col-md-8\"> {{'equipment.handles' | translate}}: <span>{{equipment.handlesNo}}</span></div>";
                         break;
                     }
                     case 'stands': {
                         html = "<div class=\"col-md-4\">{{'equipment.levels' | translate}}: <span>{{equipment.levels}}</span></div> <div class=\"col-md-8\"> {{'equipment.height' | translate}}: <span>{{equipment.heightMin + ' ' + equipment.heightMinUnit.shortName + ' - ' + equipment.heightMax + ' ' + equipment.heightMaxUnit.shortName}}</span></div>";
                         break;
                     }
                     default: {
                        html = "<div>{{equipment.id}}</div>"
                     }
                 }
                 element.replaceWith($compile(html)(scope));
             };
         }
     }
 }).directive("youtubeVideo", ['youtube', function (e) {
     return{
         restrict: 'E',
         scope: {
             videoId: "=",
             videoUrl: "="},
         link: function (t, n) {
            e.playerId = n[0].id;
            var r = t.$watch(function () {
                return e.ready && ("undefined" != typeof t.videoUrl || "undefined" != typeof t.videoId)
            }, function (n) {
                n && (r(), "undefined" != typeof t.videoUrl ? t.$watch("videoUrl", function (t) {
                e.setURL(t), e.loadPlayer()
             }) : t.$watch("videoId", function (t) {
                 e.videoId = t, e.loadPlayer()
             }))
         })
     }}
 }]);


