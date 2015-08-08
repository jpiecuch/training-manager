app.service('tableService', function() {

    this.get = function(service) {
        return {
            page: 1,
            count: 10,
            data: {
                result: [],
                count: 0
            },
            service: service,
            filter: {
                params: {
                    firstResult: 0,
                    maxResults: 10
                },
                modes: {}
            },
            totalPage: function() {
                return Math.ceil(this.data.count / this.count);
            },
            search: function() {
                var me = this;
                me.filter.params.firstResult = 0;
                this.service.retrieve(this.filter.params).then(function(data) {
                    me.page = 1;
                    me.data = data.data;
                });
            },
            sort: function(property) {
                this.page = 1;
                this.filter.params.firstResult = ((this.page - 1) * this.count);
                this.filter.params.orderby = property;
                this.filter.modes[property] = !this.filter.modes[property] || this.filter.modes[property] === 'DESC' ? 'ASC' : 'DESC';
                this.filter.params.ordermode = this.filter.modes[property];

                var me = this;
                this.service.retrieve(this.filter.params).then(function(data) {
                    me.data = data.data;
                });

            },
            changePage: function(increase) {
                this.page = new Number(this.page ? this.page : 1) + increase;
                var me = this;

                this.filter.params.firstResult = ((this.page - 1) * this.count);
                this.filter.params.maxResults = this.count;

                this.service.retrieve(this.filter.params).then(function(data) {
                    me.data = data.data;
                });
            },
            changeCount: function(count) {
                this.page = 1;
                this.count = new Number(count) + 0;
                var me = this;

                this.filter.params.firstResult = ((this.page - 1) * this.count);
                this.filter.params.maxResults = this.count;

                this.service.retrieve(this.filter.params).then(function(data) {
                    me.data = data.data;
                });
            }
        }
    }
});

