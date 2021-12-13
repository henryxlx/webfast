<#assign panel = 'dashboard'/>
<#assign script_controller = 'default/index'/>
<#include '/admin/layout.ftl'/>

<#macro blockContent>

<div class="page-header">
    <h1>管理中心  </h1>
</div>

<#if userAcl?? && userAcl.hasRole('ROLE_ADMIN')>

<div class="row">
    <div class="col-md-12">
        <div id="app-upgrade-alert" class="alert alert-info alert-dismissable hide" data-url="${ctx}/admin/app/upgrades/count" data-upgrade-url="${ctx}/admin/app/upgrades">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        </div>
    </div>

    <div class="col-md-12">
        <@renderController path='/admin/default/getCloudNotice'/>
    </div>


    <div class="col-md-6">
        <div class="panel panel-default">

            <div class="panel-heading clearfix">
     <span class="pull-right" style="display:block">
         <span id="onlineNum" data-url="${ctx}/admin/online/count">正在载入在线人数...</span>（<span id="loginNum" data-url="${ctx}/admin/login/count">正在载入登录人数...</span>）
     </span>
                <h3 class="panel-title pull-left" id="operation-analysis-title" data-url="${ctx}/admin/operation/analysis"> 数据统计

                </h3>
            </div>
            <div id="operation-analysis-table" >
                <div class="empty">正在载入数据，请稍等。</div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title" id="system-status-title" data-url="${ctx}/admin/system/status">系统状态</h3>
            </div>
            <div id="system-status" >
                <div class="empty">正在载入数据，请稍等。</div>
            </div>
        </div>
    </div>
    <div class="col-md-6"></div>
    <div id="popular-courses-panel" class="col-md-6">
        <div class="panel panel-default">
            <div class="panel-heading">
                <select id="popular-courses-type" class="pull-right" data-url="${ctx}/admin/popular/courses">
                    <@select_options dict['dateType']!{} RequestParameters['dateType']! />
                </select>
                <h3 class="panel-title">受欢迎课程</h3>
            </div>
            <div id="popular-courses-table">
                <div class="empty">正在载入数据，请稍等。</div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <renderController path='/admin/default/unsolvedQuestionsBlock'/>
</div>

<#else>
<div class="row">
    <div class="col-md-12">
        <h4>欢迎来到${setting('site.name', '管理后台')}</h4>
    </div>
</div>
</#if>

</#macro>