define(function(require, exports, module) {
	var Notify = require('common/bootstrap-notify');

	exports.run = function() {
		$("#content-table").on('click', '[data-role=trash-item]', function(){
			$.post($(this).data('url'), function (retData) {
				if (retData == true) {
					Notify.success('内容移至回收站成功！');
					setTimeout(function () { window.location.reload(); }, 1000);
				} else {
					Notify.danger('内容移至回收站失败！');
				}
			}).error(function (response) {
				Notify.danger('内容移至回收站时发生错误，请检查后重试！');
			});
		});

		$("#content-table").on('click', '[data-role=publish-item]', function(){
			$.post($(this).data('url'), function (retData) {
				if (retData == true) {
					Notify.success('资讯发布成功！');
					setTimeout(function () { window.location.reload(); }, 1000);
				} else {
					Notify.danger('资讯发布失败！');
				}
			}).error(function (response) {
				Notify.danger('资讯发布时发生错误，请检查后重试！');
			});
		});

		$("#content-table").on('click', '[data-role=delete-item]', function(){
			if (!confirm('真的要永久删除该内容吗？')) {
				return ;
			}
			$.post($(this).data('url'), function (retData) {
				if (retData == true) {
					Notify.success('内容删除成功！');
					setTimeout(function () { window.location.reload(); }, 1000);
				} else {
					Notify.danger('内容删除失败！');
				}
			}).error(function (response) {
				Notify.danger('内容删除时发生错误，请检查后重试！');
			});
		});

	};

});