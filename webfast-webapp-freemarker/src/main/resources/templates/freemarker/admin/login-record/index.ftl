<#assign menu = 'login_record'/>
<#assign script_controller = 'login-record/list'/>

<#include '/admin/user/layout.ftl'/>
<#macro blockTitle>登录日志 - ${blockTitleParent}</#macro>

<#macro blockMain>
<div class="page-header">
    <h1>登录日志</h1>
</div>

<div class="well well-sm">
    <form id="login-record-search-form" class="form-inline" action="" method="get" novalidate>


        <div class="form-group">
            <select id="keywordType" name="keywordType" class="form-control">
                <@select_options dict['userKeyWordType']!{} RequestParameters['keywordType']! />
            </select>
        </div>

        <div class="form-group">
            <input type="text" id="keyword" name="keyword" class="form-control" value="${RequestParameters['keyword']!}" placeholder="关键词">
        </div>

        <div class="form-group">
            <input class="form-control" type="text" id="startDate" name="startDateTime" value="${RequestParameters['startDateTime']!}" placeholder="起始时间">
        </div>

        <div class="form-group">
            <input class="form-control" type="text" id="endDate" name="endDateTime" value="${RequestParameters['endDateTime']!}" placeholder="结束时间">
        </div>
        <span></span>

        <button class="btn btn-primary">搜索</button>
    </form>
</div>



<table id="teacher-table" class="table table-striped table-hover" data-search-form="#user-search-form">
    <thead>
    <tr>
        <th>用户名</th>
        <th>用户邮箱</th>
        <th>登录时间IP</th>
        <th>登录地点</th>
        <th width="8%">查看记录</th>
    </tr>
    </thead>
    <tbody>
    <#list logRecords! as logRecord>
    {% set user = users[logRecord.userId]|default(null) %}
    {% include 'TopxiaAdminBundle:LoginRecord:table-tr.html.twig' with {logRecord:logRecord} %}
    </#list>
    </tbody>
</table>
<@web_macro.paginator paginator! />
</#macro>