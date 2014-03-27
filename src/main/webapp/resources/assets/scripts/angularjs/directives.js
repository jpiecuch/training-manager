training.directive('ngUniform',function($timeout){
  return {
    restrict:'A',
    link: function(scope, element, ngModel) {
        jQuery(element).uniform({useID: false});
        
        }
  };
}).directive('ngSpinner', function() {
    return {
        restrict: 'A',
        link: function(scope, element) {
            jQuery(element).spinner({min: 0});
            jQuery(element).numeric({negative: false});
        }
    };
}).directive('ngCalendarEvent', function() {
    return {
        restrict: 'A',
        link: function(scope, element) {
            var eventObject = { title: jQuery.trim(jQuery(element).text()) };
            jQuery(element).data('eventObject', eventObject);
            jQuery(element).draggable({ zIndex: 999, revert: true, revertDuration: 0, appendTo: 'body', helper: 'clone', 
            start: function(e, ui) {
                 jQuery(ui.helper).removeClass("label-success");
                 jQuery(ui.helper).addClass("label-info");
                 ui.position.top -= $(document).scrollTop();
            },
            drag: function(event, ui) {
                    ui.position.top -= $(document).scrollTop();
            }
            });  
        }
    };
}).directive('ngVerticalDraggable', function() {
    return {
      restrict: 'A',
      link: function(scope, element, attrs) {
          jQuery(element).draggable({ 
            axis: "y",
            containment: attrs.ngVerticalDraggable
        });
      }
    };
}).directive('ngScroller', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            jQuery(element).slimScroll({
                allowPageScroll: true,
                size: '7px',
                color: attrs.dataHandleColor ? attrs.dataHandleColor : '#bbb',
                railColor: attrs.dataRailColor ? attrs.dataRailColor : '#eaeaea',
                height: attrs.dataHeight ? attrs.dataHeight : attrs.height,
                alwaysVisible: attrs.dataAlwaysVisible == "1" ? true : false,
                railVisible: attrs.dataRailVisible == "1" ? true : false,
                disableFadeOut: true
            });
        }
    };
});

