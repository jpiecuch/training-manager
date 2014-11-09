training.controller("loginController", function($scope, $http, $rootScope, urlService, authenticateService, resourceService, inputValidateService, policyService) {

    $scope.login = {
        tab: {
            idx: 0,
            setIdx: function(idx) {
                this.idx = idx;
            }
        },
        bgs: '',
        social: {
            providers:  [],
            url: ''
        },
        reset: {
            post: function() {
                authenticateService.resetPassword(this.email).then(function(data) {

                });
            },
            email: null
        },
        create: {
            post: function() {
                var me = this;
                authenticateService.create(this.login.create.user).then(function(data) {
                    this.login.create.status = 'CREATED';
                });
            },
            user: null,
            status: null
        },
        input: {
            isNotValid: function(input) {
                return inputValidateService.isNotValid(input);
            },
            status: function(input) {
                return inputValidateService.status(input);
            }
        },
        policy: {
            show: function() {
                policyService.show();
            }
        }
    };

    $scope.init = function() {
        resourceService.getAll('/resource/names/image/start-bg').then(function(response) {
            var bgs = [];
            for(var i = 0; i < response.data.length; i++) {
                bgs.push(urlService.url('resource/content/start-bg/') + response.data[i]);
            }
            $scope.login.bgs = bgs.join();
        });

        authenticateService.getSocials().then(function(data) {
            $scope.login.social.providers = data.data;
            $scope.login.social.url = urlService.url('/signin');
        });
    };
}).controller("policyController", function($scope, $http, $modal) {

    $scope.init = function(isOpen) {
        if (isOpen) {
            open();
        }
    };

    $scope.$on('showPolicyEvent', function () {
        open();
    });

    var open = function() {
        var modalInstance = $modal.open({
            templateUrl: 'policy.html',
            controller: policyModalController,
            size: 'lg'
        });
        modalInstance.result.then(function () {});
    }

}).controller('footerController', function($scope, $http, $rootScope) {
    $scope.link = function(type) {
        $rootScope.$broadcast(type === 'policy' ? 'showPolicyEvent' : 'showTermsEvent');
    }
}).controller("CalendarCtrl", function($scope, $http, $translate, urlService, $location, lang) {
    $scope.eventSources = [];
    $scope.lang = lang;

    $scope.startDrag = function(event, ui) {
        ui.helper.css('background-color', '#28b779');
        ui.helper.css('width', $('.exercise-event').width());
    };

    $scope.init = function(lang) {
        $scope.page = 0;
        $scope.lastPage = true;
        $translate.use(lang);
        var dragEventStartDate;
        var currentDate = new Date();
        currentDate.setHours(0,0,0,0);
        $scope.uiConfig = {
            draggable: {onStart: 'startDrag'},
            event: { zIndex: 999, revert: true, revertDuration: 0, appendTo: 'body', helper: 'clone' },
            eventBox : { axis: 'y', containment: '#calendar-row' },
            calendar: {
                header: { left: 'title', center: '', right: 'prev, next,today' },
                editable: true,
                disableResizing: true,
                selectable: false,
                droppable: true,
                eventDragStart: function(event) { dragEventStartDate = new Date(event.start); },
                eventDrop: function(event, dayDelta, minuteDelta, allDay, revertFunc) {
                    if (dragEventStartDate < currentDate || event.start < currentDate) {
                        revertFunc();
                    } else {
                        $http.post(urlService.apiURL('/calendar/event/move') , { id: event.id, start: moment(event.start).format('YYYY-MM-DD'), title: null, allDay: null }).error(function() { revertFunc(); });
                    }
                },
                eventDragStop: function(event, jsEvent, ui, view) {
                    var a = ui.offset;
                    var area = angular.element(document.querySelector('#calendar'));
                    var b = area.offset();
                    b.right = area.outerWidth() + b.left;
                    b.bottom = area.outerHeight() + b.top;
                    if (!(a.left >= b.left && a.top >= b.top && a.left <= b.right && a.top <= b.bottom)) {
                        $http.post(urlService.apiURL('/calendar/event/remove'), { id: event.id }).success(function() {
                            $scope.calendar.fullCalendar('removeEvents', event.id);
                            $scope.eventSources[0].splice($scope.eventSources[0].indexOf(event), 1);
                            var counter = 1;
                            for (var i = 0; i < $scope.eventSources[0].length; i++) {
                                var current = $scope.eventSources[0][i];
                                if (moment(event.start).format('YYYY-MM-DD') === moment(current.start).format('YYYY-MM-DD') && event.id !== current.id) {
                                    current.title = (counter++) + '.' + current.title.split('.')[1];
                                }
                            }
                        });
                    }
                },
                drop: function(date, allDay, jsEvent, ui) {
                    $http.post(urlService.apiURL('/calendar/event/create'), { id: parseInt(ui.helper.attr('value')), start: moment(date).format('YYYY-MM-DD'), title: null, allDay: null }).success(function(event) {
                        $scope.eventSources[0].push(event);
                        $scope.calendar.fullCalendar('renderEvent', event, true);
                    });
                },
                dayClick: function(date) {
                    $location.path('calendar/day/' + moment(date).format('YYYY-MM-DD')).search('position', 1);
                },
                eventClick: function(event, jsEvent, view) {
                    $location.path('calendar/day/' + moment(event.start).format('YYYY-MM-DD')).search('position', event.title.split('.')[0]);
                },
                viewRender: function(view, element) {
                    $http.get(urlService.apiURL('/calendar/event/start/' + moment(view.visStart).format('YYYY-MM-DD') + '/end/' + moment(view.visEnd).format('YYYY-MM-DD'))).success(function(data) {
                        $scope.eventSources[0] = data;
                    });
                }
            }
        };

        $http.get(urlService.apiURL('/dictionary/partymuscles')).success(function(pm) {
            $scope.partyMuscles = pm;
        });
    };

    $scope.update = function(increment) {
        var maxResults = 8;
        $scope.page += increment ? increment : $scope.page * -1;
        $http.get(urlService.apiURL('/dictionary/exercise/first/'+$scope.page * maxResults +'/max/'+maxResults+'/partymuscles/' + $scope.p)).success(function(exercises) {
            $scope.exercises = exercises.result;
            $scope.lastPage = exercises.count / maxResults < $scope.page + 1;
        });
    }
}).controller("RecordsCtrl", function($scope, $http, $sce, $modal, $timeout, $translate, dayService, urlService, $routeParams, $location, $route, alertService) {
    var chartColors = ['#88bbc8', '#ed7a53', '#9FC569', '#bbdce3', '#9a3b1b', '#5a8022', '#2c7282'];
    var equipment = {};
    $scope.link = function(src) {return $sce.trustAsResourceUrl(src);};

    $scope.addEquipment = { loads: [], bars: [], dumbbells: [], necks: [], stands: [], benches: [], press: [] };
    $scope.deleteEquipment = { loads: [], bars: [], dumbbells: [], necks: [], stands: [], benches: [], press: [] };

    $scope.stopwatch = [];

    $scope.portlets = {
        video: {
            visibility: true
        },
        description: {
            visibility: false
        },
        comments: {
            visibility: false
        },
        updateState: function(portlet) {
            if (portlet === 'comments') {
                $http.get(urlService.apiURL('/dictionary/exercise/' + $scope.tab.exercise.id + '/comment/first/0/max/10')).success(function (data) {
                   $scope.comments = data.result;
                });
            }
            $scope.portlets[portlet].visibility = !$scope.portlets[portlet].visibility;
        }
    }

    $scope.init = function() {
        updateLoading(false);
        $scope.stopwatchFlag = false;
        $http.get(urlService.apiURL('/day/exercise/' + $routeParams.date)).success(function(data) {
            var position = $routeParams.position;
            $scope.date = $routeParams.date;
            $scope.dayExercises = data;
            $scope.tab = !isNaN(position) && parseInt(Number(position)) == position && position <= data.length && position > 0 ? data[position - 1] : data[0];
            $http.get(urlService.apiURL('/dictionary/equipment')).success(function(data) {
                equipment = data;
                prepareTab();
            });

            $http.get(urlService.apiURL('/dictionary/equipment/type')).success(function(data) {
                $scope.equipmentTypes = data;
            });
        });
    };

    $scope.description = function(d) {
        return $sce.trustAsHtml(d);
    }

    $scope.changeTab = function(d) {
        $scope.tab = $scope.dayExercises[d.position - 1];
        clear($scope.addEquipment);
        clear($scope.deleteEquipment);
        $scope.comments = [];
        $scope.portlets.comments.visibility = false;
        $scope.portlets.description.visibility = false;
        $scope.portlets.video.visibility = true;
        $scope.message = null;
        prepareTab();
    };

    $scope.postComment = function() {
        $http.post(urlService.apiURL('/dictionary/exercise/comment/'),
            {
                comment: $scope.message,
                commented: {
                    id: $scope.tab.exercise.id
                }
            }).success(function(data) {
            $scope.comments.push(data);
        });
    }

    function prepareTab() {
        $scope.equipment = angular.copy(equipment);
        updateArrays($scope.tab.loads, $scope.equipment.loads, null, false);
        updateArrays($scope.tab.bars, $scope.equipment.bars, null, false);
        updateArrays($scope.tab.dumbbells, $scope.equipment.dumbbells, null, false);
        updateArrays($scope.tab.necks, $scope.equipment.necks, null, false);
        updateArrays($scope.tab.stands, $scope.equipment.stands, null, false);
        updateArrays($scope.tab.benches, $scope.equipment.benches, null, false);
        updateArrays($scope.tab.press, $scope.equipment.press, null, false);
    }

    $scope.delete = function(d) {
        updateArrays($scope.deleteEquipment.loads, d.loads, $scope.equipment.loads, true);
        updateArrays($scope.deleteEquipment.bars, d.bars, $scope.equipment.bars, true);
        updateArrays($scope.deleteEquipment.dumbbells, d.dumbbells, $scope.equipment.dumbbells, true);
        updateArrays($scope.deleteEquipment.necks, d.necks, $scope.equipment.necks, true);
        updateArrays($scope.deleteEquipment.stands, d.stands, $scope.equipment.stands, true);
        updateArrays($scope.deleteEquipment.benches, d.benches, $scope.equipment.benches, true);
        updateArrays($scope.deleteEquipment.press, d.press, $scope.equipment.press, true);
        d.totalWeight = dayService.totalWeight(d);
    };

    $scope.add = function() {
        updateArrays($scope.addEquipment.loads, $scope.equipment.loads, $scope.tab.loads, true);
        updateArrays($scope.addEquipment.bars, $scope.equipment.bars, $scope.tab.bars, true);
        updateArrays($scope.addEquipment.dumbbells, $scope.equipment.dumbbells, $scope.tab.dumbbells, true);
        updateArrays($scope.addEquipment.necks, $scope.equipment.necks, $scope.tab.necks, true);
        updateArrays($scope.addEquipment.stands, $scope.equipment.stands, $scope.tab.stands, true);
        updateArrays($scope.addEquipment.benches, $scope.equipment.benches, $scope.tab.benches, true);
        updateArrays($scope.addEquipment.press, $scope.equipment.press, $scope.tab.press, true);
        $scope.tab.totalWeight = dayService.totalWeight($scope.tab, true);
    };

    $scope.addSeries = function(d) {
        d.series[d.series.length] = {};
    };

    $scope.removeSeries = function(d, idx) {
        d.series.splice(idx, 1);
    }

    $scope.save = function(d, message) {
        updateLoading();
        $http.post(urlService.apiURL('/day/exercise'), d).success(function(data) {
            d.version++;
            d.series = data.series;
            alertService.show({title: 'OK!', description: !message ? 'day.exercise.save' : message, type: 'info'});
            updateLoading();
        });
    };

    $scope.confirm = function(d) {
        if ($scope.stopwatch[d.id]) {
            $scope.stop(d);
        }
        d.confirmed = true;
        $scope.save(d, 'day.exercise.confirm');
    };

    $scope.show = function(array, object) {
        for (var i = 0; i < array.length; i++) {
            if (array[i].id === object.id) {
                return false;
            }
        }
        return true;
    };

    $scope.empty = function(e) {
        return e.loads.length === 0 && e.bars.length === 0 && e.dumbbells.length === 0 && e.necks.length === 0 && e.stands.length === 0 && e.benches.length === 0 && e.press.length === 0;
    };

    $scope.progress = function(d) {
        $modal.open({
            template: '<div class="modal-header"><button type="button" class="close" ng-click="ok()"></button><h4 class="modal-title"><spring:message code="exercise.progress"/></h4></div><div class="modal-body"><div class="progress-chart" style="height: 400px; width: 870px"></div></div>',
            controller: ModalInstanceCtrl,
            windowClass: 'progress-modal'
        });

        $http.get(urlService.apiURL('/day/exercise/'+ d.exercise.id +'/progress/')).success(function(progressData) {
            var chartLine = new Array();
            var chartWeightLine = new Array();
            for (var i = 0; i < progressData.length; i++) {
                var date = new Date(14400000 + progressData[i].date);//TODO shit idea
                var weight = progressData[i].totalWeight !== 0.0 ? progressData[i].totalWeight : 1.0;
                var series = 0;
                for(var j = 0; j < progressData[i].series.length; j++) {
                    series += parseInt(progressData[i].series[j].value);
                }
                chartWeightLine.push([date, weight]);
                chartLine.push([date, weight * series / (12 * progressData[i].series.length)]);
            }

            var options = {
                grid: { show: true, aboveData: true, color: "#3f3f3f", labelMargin: 5, axisMargin: 0, borderWidth: 0, borderColor: null,
                    minBorderMargin: 5, clickable: true, hoverable: true, autoHighlight: true, mouseActiveRadius: 20 },
                series: {
                    grow: { active: false, stepMode: "linear", steps: 50, stepDelay: true },
                    lines: { show: true, fill: false, lineWidth: 4, steps: false },
                    points: { show: true,radius: 5, symbol: "circle", fill: true, borderColor: "#fff" }
                },
                legend: { position: "se" },
                yaxis: { },
                xaxis: { mode: "time", timeformat: "%d-%m-%Y", minTickSize: [1, "day"], timezone: "Europe/Warsaw" },
                colors: chartColors,
                shadowSize: 1,
                tooltip: true,
                tooltipOpts: { content: "%x: %y.0", xDateFormat: "%d-%m-%Y", shifts: { x: -30, y: -50 } }
            };

            jQuery.plot(jQuery(".progress-chart"), [
                {
                    label: "Postęp w ćwiczeniu (współczynnik)",
                    data: chartLine,
                    lines: { fillColor: "#f2f7f9" },
                    points: { fillColor: "#88bbc8" }
                },
                {
                    label: "Postęp w ćwiczeniu (waga)",
                    data: chartWeightLine,
                    lines: { fillColor: "#fff8f2" },
                    points: { fillColor: "#ed7a53" }
                }
            ], options);

        });
    };

    $scope.start = function (d) {
        $scope.stopwatch[d.id] = $timeout(function() {
            d.time += 1000;
            $scope.start(d);
        }, 1000);
    };

    $scope.stop = function (d) {
        $timeout.cancel($scope.stopwatch[d.id]);
        $scope.stopwatch[d.id] = null;
    };

    $scope.checkAll = function(checkObject, useObject, name) {
        if (checkObject[name].length === useObject[name].length) {
            checkObject[name] = [];
        } else {
            checkObject[name] = angular.copy(useObject[name]);
        }
    };

    $scope.socialShare = function(social) {
        $scope.sharing = true;
        updateLoading();
        $http.post(urlService.apiURL('/day/exercise/social/' + social + '/' + $scope.tab.id)).success(function() {
            $scope.sharing = false;
            updateLoading();
        }).error(function() {
            $scope.sharing = false;
            updateLoading();
        });
    }

    function updateLoading(flag) {
        $scope.loading = flag === undefined ? !$scope.loading : flag;
    }

    function updateArrays(items, reducesArray, increasedArray, clear) {
        for(var i = 0; i < items.length; i++) {
            if (reducesArray !== null) {
                reducesArray.splice(_.findIndex(reducesArray, {id : items[i].id}), 1);
            }
            if (increasedArray !== null) {
                increasedArray.push(items[i]);
            }
        }
        if (clear) {
            items.length = 0;
        }
    }

    function clear(object) {
        object = { loads: [], bars: [], dumbbells: [], necks: [], stands: [], benches: [], press: [] };
    }
}).controller("ExercisesCtrl", function($scope, $http, contextPath, ngTableParams, $filter, $modal, $sce, $translate, urlService) {

    $scope.resetForm = function() {
        $scope.exercise = {
            names: { pl: '', en: '' },
            movieUrl: ''
        };
    };

    $scope.init = function(lang) {
        $translate.use(lang);

        $scope.tableParams = new ngTableParams({
            page: 1,
            count: 10
        }, {
            getData: function($defer, params) {
                var first = ((params.page() - 1) * params.count());
                var max = params.count();
                $http.get(urlService.apiURL('/dictionary/exercise/first/' + first + '/max/' + max)).success(function(data) {
                    var orderedData = params.sorting() ? $filter('orderBy')(data.result, params.orderBy()) : data.result;
                    $defer.resolve(orderedData);
                    params.lastPage = first + max >= data.count;
                    params.firstPage = params.page() === 1;
                });
            }
        });

        $http.get(urlService.apiURL('/dictionary/partymuscles')).success(function(data) {
            $scope.partyMuscles = data;
        });

        $scope.resetForm();
    };

    $scope.save = function() {
        $http.post(urlService.apiURL('/dictionary/exercise'), $scope.exercise).success(function (data) {
        });
    };

    $scope.details = function(e) {
        $scope.exercise = e;
    }
}).controller("PlanCtrl", function($scope, $http, urlService, alertOptions, alertService) {
    $scope.weekdays = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday', 'sunday'];

    $scope.init = function() {
        $http.get(urlService.apiURL('/dictionary/plan/2')).success(function(data) {
            $scope.plan = data;
        });
        alertService.show({title: 'BICEPS', description: 'day.exercise.save', type: 'info'});

    };
}).controller('DashboardCtrl', function() {

});
