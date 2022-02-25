define(function(require, exports, module) {
	var Notify = require('common/bootstrap-notify');

	exports.run = function() {
		$("#article-table").on('click', '[data-role=trash-item]', function(){
			$.post($(this).data('url'), function (retData) {
				if (retData == true) {
					Notify.success('资讯移至回收站成功！');
					setTimeout(function () { window.location.reload(); }, 1000);
				} else {
					Notify.danger('资讯移至回收站失败！');
				}
			}).error(function (response) {
				Notify.danger('资讯移至回收站时发生错误，请检查后重试！');
			});
		});

		$("#article-table").on('click', '[data-role=publish-item]', function(){
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

		$("#article-table").on('click', '[data-role=unpublish-item]', function(){
			$.post($(this).data('url'), function (retData) {
				if (retData == true) {
					Notify.success('资讯取消发布成功！');
					setTimeout(function () { window.location.reload(); }, 1000);
				} else {
					Notify.danger('资讯取消发布失败！');
				}
			}).error(function (response) {
				Notify.danger('资讯取消发布时发生错误，请检查后重试！');
			});
		});	

		$("#article-table").on('click', '[data-role=delete-item]', function(){
			if (!confirm('真的要永久删除该内容吗？')) {
				return ;
			}
			$.post($(this).data('url'), function (retData) {
				if (retData == true) {
					Notify.success('删除资讯成功！');
					setTimeout(function () { window.location.reload(); }, 1000);
				} else {
					Notify.danger('删除资讯失败！');
				}
			}).error(function (response) {
				Notify.danger('删除资讯时发生错误，请检查后重试！');
			});
		});

		$(".featured-label").on('click', function() {
			var $self = $(this);
			var span = $self.find('span');
			var spanClass = span.attr('class');
			var postUrl = "";

			if(spanClass == "label label-default"){
				postUrl = $self.data('setUrl');
				$.post(postUrl, function(response) {
					var labelStatus = "label label-success";
					span.attr('class',labelStatus)
				});
			}else{
				postUrl = $self.data('cancelUrl');
				$.post(postUrl, function(response) {
					var labelStatus = "label label-default";
					span.attr('class',labelStatus)
				});
			}
		});		

		$(".promoted-label").on('click', function(){

			var $self = $(this);
			var span = $self.find('span');
			var spanClass = span.attr('class');
			var postUrl = "";

			if(spanClass == "label label-default"){
				postUrl = $self.data('setUrl');
				$.post(postUrl, function(response) {
					var labelStatus = "label label-success";
					span.attr('class',labelStatus)
				});
			}else{
				postUrl = $self.data('cancelUrl');
				$.post(postUrl, function(response) {
					var labelStatus = "label label-default";
					span.attr('class',labelStatus)
				});
			}

		});		

		$(".sticky-label").on('click', function(){
		
			var $self = $(this);
			var span = $self.find('span');
			var spanClass = span.attr('class');
			var postUrl = "";
			
			if(spanClass == "label label-default"){
				postUrl = $self.data('setUrl');
				$.post(postUrl, function(response) {
					var labelStatus = "label label-success";
					span.attr('class',labelStatus)
				});
			}else{
				postUrl = $self.data('cancelUrl');
				$.post(postUrl, function(response) {
					var labelStatus = "label label-default";
					span.attr('class',labelStatus)
				});
			}
		});

		var $container = $('#aticle-table-container');
		var $table = $('#article-table');
		 require('../../util/batch-select')($container);
		 require('../../util/batch-delete')($container);
		 require('../../util/item-delete')($container);
	};

});