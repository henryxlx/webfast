define(function(require, exports, module) {

	exports.run = function() {

		$("#permission-table").on('click', '[data-role=delete-item]', function(){
			if (!confirm('真的要永久删除该内容吗？')) {
				return ;
			}
			$.post($(this).data('url'), function(){
				window.location.reload();
			});
		});
	};

});