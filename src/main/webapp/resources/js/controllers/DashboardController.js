'use strict';

app.controller('DashboardController', function($scope, userWorkoutService, queryValueService) {
    
    $scope.init = function() {
        $scope.dashboard = {};

        $scope.columns = [
            [
                {
                    type: 'WORKOUTS',
                    query: {
                        from: {
                            type: 'DATE',
                            value: 'TODAY'
                        },
                        state: {
                            type: 'ARRAY',
                            value: [
                                {
                                    type: 'STRING',
                                    value: 'PLANNED'
                                },
                                {
                                    type: 'STRING',
                                    value: 'IN_PROGRESS'
                                }]
                        },
                        maxResults: {
                            type: 'NUMBER',
                            value: 5
                        }
                    }
                },
                {
                    type: 'SERIAL_CHART',
                    dataType: 'WEIGHT',
                    query: {
                        to: {
                            type: 'DATE',
                            value: 'TODAY'
                        },
                        type: {
                            type: 'STRING',
                            value: 'WEIGHT'
                        },
                        maxResults: {
                            type: 'NUMBER',
                            value: 50
                        }
                    }
                }
            ],
            [
                {
                    type: 'SERIAL_CHART',
                    dataType: 'WAIST',
                    query: {
                        to: {
                            type: 'DATE',
                            value: 'TODAY'
                        },
                        type: {
                            type: 'STRING',
                            value: 'WAIST'
                        },
                        maxResults: {
                            type: 'NUMBER',
                            value: 50
                        }
                    }
                },
                {
                    type: 'PIE_CHART',
                    query: {
                        groupBy: {
                            type: 'STRING',
                            value: 'exercise.description.muscles'
                        }
                    }
                },
                {
                    type: 'PIE_CHART',
                    query: {
                        groupBy: {
                            type: 'STRING',
                            value: 'state'
                        }
                    }
                }
            ],
            [

            ]
        ];
    };
    

    

    
});