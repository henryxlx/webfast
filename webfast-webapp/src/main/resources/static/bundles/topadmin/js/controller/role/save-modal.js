define(function(require, exports, module) {

	var Validator = require('bootstrap.validator');
    var Notify = require('common/bootstrap-notify');
	require('common/validator-rules').inject(Validator);
	exports.run = function() {
		var $form = $('#role-form');
		var $modal = $form.parents('.modal');
        var $table = $('#role-table');

		var validator = new Validator({
            element: $form,
            autoSubmit: false,
            onFormValidated: function(error, results, $form) {
                if (error) {
                    return ;
                }

                $('#role-create-btn').button('submiting').addClass('disabled');

                $.post($form.attr('action'), $form.serialize(), function(html){
                    var $html = $(html);
                    if ($table.find( '#' +  $html.attr('id')).length > 0) {
                        $('#' + $html.attr('id')).replaceWith($html);
                        Notify.success('角色更新成功！');
                    } else {
                        $table.find('tbody').prepend(html);
                        Notify.success('角色添加成功!');
                    }
                    $modal.modal('hide');
				});

            }
        });

        validator.addItem({
            element: '#role-name-field',
            required: true,
            rule: 'remote'
        });

	};
});