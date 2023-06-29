<#assign menu = 'reinstall'/>
<#assign script_controller = 'app/reinstall'/>

<@block_title '重新安装应用'/>

<#include '/admin/app/layout.ftl'/>

<#macro blockMain>

    <div class="page-header"><h1>重新安装应用</h1></div>

    <@web_macro.flash_messages />

    <h1><small>谨慎操作区域</small></h1>
    <hr/>

    <div class="panel panel-default">
        <div class="panel-heading ">
            <h3 class="panel-title text-warning">请仔细确认后再操作</h3>
        </div>
        <div class="panel-body">
            <p class="text-warning">
                慎重：请确保当前系统已经备份系统文件和数据库。当系统重新安装后，系统的外部文件和数据库将被清除。
            </p>
            <p>
                系统重新进入安装过程。通过重新安装新的数据库数据和设置外部系统文件，数据库中已有的数据和外部系统可能会丢失。
            <p/>
            <p >
                <button id="appReinstall" class="btn btn-warning fr" data-url="${ctx}/admin/app/reinstall">进入安装程序</button>
            </p>
        </div>
    </div>

</#macro>