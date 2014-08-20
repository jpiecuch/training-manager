$(document).ready(function() {
    if ($.browser.mozilla) {
        $("body").draggable({
            drag: function (event, ui) {
                return false;
            }
        });
    }
});
