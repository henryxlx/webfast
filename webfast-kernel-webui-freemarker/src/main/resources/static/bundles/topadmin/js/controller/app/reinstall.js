define(function(require, exports, module) {

    var Notify = require('common/bootstrap-notify');

    exports.run = function () {

        $('#appReinstall').on('click', function () {
            $.post($(this).data('url'), function (retData) {
                if (retData == true) {
                    Notify.success('准备开始重新安装应用！');
                    window.location.reload();
                } else {
                    Notify.danger('重新安装应用失败！');
                }
            }).error(function (response) {
                Notify.danger('重新安装应用失败！');
            });
        });
    }
});