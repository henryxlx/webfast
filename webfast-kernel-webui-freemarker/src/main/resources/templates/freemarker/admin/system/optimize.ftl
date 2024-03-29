<#assign menu = 'optimize'/>
<#assign script_controller = 'optimize/optimize'/>

<@block_title '优化和备份'/>

<#include '/admin/system/layout.ftl'/>

<#macro blockMain>

    <div class="page-header"><h1>优化和备份</h1></div>

    <@web_macro.flash_messages />

    <div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">刷新缓存</h3>
  </div>
  <div class="panel-body">
  	<p>
      刷新并重建缓存,让系统保持最优的状态。 
    </p>
    <p >
  		<button id="removecache" class="btn btn-primary fr " data-url="${ctx}/admin/optimize/remove-cache">刷新缓存</button>
  	</p>
  </div>
</div>

	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">优化磁盘空间</h3>
	  </div>
	  <div class="panel-body">
	   	<p>
	       移除系统的临时数据，释放出更多磁盘空间。
	    </p>
	    <p >
	  		<button id="removeTmp" class="btn btn-primary fr" data-url="${ctx}/admin/optimize/remove-tmp">优化磁盘空间</button>
	  	</p>
	  </div>
	</div>


	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">数据库备份和下载</h3>
		</div>
		<div class="panel-body">
			<p>
				备份数据库，并下载备份文件。 下载完成后请<span class="text-info">优化磁盘空间</span>。
			</p>
			<p>
				<button id="backupDatabase" class="btn btn-primary fr" data-url="${ctx}/admin/optimize/backupdb">
					数据库备份和下载
				</button>
			</p>
			<p>
				<span id="dbbackup" class="glyphicon glyphicon-download-alt hide"><a id="dbdownload" href="#"
																					 target="_blank">下载数据库备份</a></span>
			</p>
		</div>
	</div>

<#--
<hr/>
<h1><small>谨慎操作区域</small></h1>
<hr/>
-->

<#if disabled_features?? && disabled_features?seq_contains('upgrade')>
	<div class="panel panel-default">
	  <div class="panel-heading ">
	    <h3 class="panel-title text-warning">清空系统备份数据</h3>
	  </div>
	  <div class="panel-body">
	   	<p class="text-warning">
	       慎重：请确保当前系统已经升级成功。当系统升级过程中发生意外，备份的系统文件和数据库可用于恢复。
	    </p>   	
	    <p>
	       清空系统升级过程中备份的系统文件和备数据库。通过清空系统备份数据，可以让系统释放出更多的磁盘空间
	    <p/>
	    <p >
	  		<button id="removeBackup" class="btn btn-warning fr" data-url="${ctx}/admin/optimize/remove-backup">清空系统备份数据</button>
	  	</p>
	  </div>
	</div>
</#if>


</#macro>