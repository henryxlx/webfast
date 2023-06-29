<#assign submenu = 'offline'/>

<@block_title '离线设置'/>

<#include '/admin/system/site-layout.ftl'/>

<#macro blockMainContent>
    <@web_macro.flash_messages />

    <div class="page-header"><h1>离线设置</h1></div>

    <form class="form-horizontal" id="site-offline-form" action="${ctx}/admin/setting/site/offline" method="post">

        <div class="row form-group">
			<div class="col-md-3 control-label">
				<label >应用运行状态</label>
			</div>
			<div class="controls col-md-8 radios mode-radios">
				<@radios 'offline' {'false':'在线', 'true':'离线'} offline?c />
				<div class="help-block">请注意：离线状态设置后除管理员外的其它用户无法访问应用！</div>
			</div>
		</div>

		<div class="row form-group">
			<div class="col-md-3 control-label"></div>
			<div class="controls col-md-8">
				<button type="submit" class="btn btn-primary">提交</button>
			</div>
		</div>

	    <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">

	</form>

</#macro>