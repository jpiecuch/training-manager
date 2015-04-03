app.service('videoProviderService', function() {
    var youtubeRegexp = /https?:\/\/(?:[0-9A-Z-]+\.)?(?:youtu\.be\/|youtube(?:-nocookie)?\.com\S*[^\w\s-])([\w-]{11})(?=[^\w-]|$)(?![?=&+%\w.-]*(?:['"][^<>]*>|<\/a>))[?=&+%\w.-]*/ig;

    this.getProvider = function(url) {
        if (url && url.match(youtubeRegexp)) {
            return 'youtube';
        }
    }
});