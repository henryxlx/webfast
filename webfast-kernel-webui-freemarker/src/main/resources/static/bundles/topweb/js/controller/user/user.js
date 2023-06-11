define(function(require, exports, module) {

    var Notify = require('common/bootstrap-notify');

    exports.run = function () {
        $('.follow-btn').on('click', function () {
            var $this = $(this);
            $.post($this.data('url'), function (resData) {
                if (resData.status == 'ok') {
                    $this.hide();
                    $('.unfollow-btn').show();
                } else {
                    Notify.danger(resData.message);
                }
            }).error(function () {
                Notify.danger('关注失败，请重试！');
            });
        });


        $('.unfollow-btn').on('click', function() {
            var $this = $(this);
            $.post($this.data('url'), function (resData) {
                if (resData.status == 'ok') {
                    $this.hide();
                    $('.follow-btn').show();
                } else {
                    Notify.danger(resData.message);
                }
            }).error(function () {
                Notify.danger('取消关注失败，请重试！');
            });
        });


    }

});