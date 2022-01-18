define(function(require, exports, module) {

    var Notify = require('common/bootstrap-notify');

	exports.run = function() {
        var $form = $("#role-permissions-form");

        $form.on('submit', function() {
            var $modal = $('#modal');

            $('#change-role-permissions-btn').button('submiting').addClass('disabled');
            $.post($form.attr('action'), $form.serialize(), function(html) {

                $modal.modal('hide');
                Notify.success('用户权限保存成功');
                var $tr = $(html);
                $('#' + $tr.attr('id')).replaceWith($tr);
            }).error(function(){
                Notify.danger('操作失败');
            });

            return false;
        });

	};

});