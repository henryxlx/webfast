<#assign menu = 'approval'/>
<#assign submenu = 'approved'/>
<#assign script_controller = 'user/approved'/>

<#include '/admin/user/layout.ftl'/>
<#macro blockTitle>用户认证审核 - ${blockTitleParent}</#macro>

<#macro blockMain>

<#include '/admin/user/approval-header.ftl'/>

<#if users??>
<table id="user-table" class="table table-striped">
    <thead>
    <tr>
        <th>ID</th>
        <th>用户昵称</th>
        <th>真实姓名</th>
        <th>身份证号码</th>
        <th>邮件</th>
        <th>申请时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    {% for user in users %}
    <tr id="user-table-tr-{{ user.id }}">
        <td>{{user.id }}</td>
        <td><strong>{{ admin_macro.user_link(user) }}</strong></td>
        <td>{{userProfiles[user.id].truename}}</td>
        <td>{{userProfiles[user.id].idcard}}</td>
        <td>{{user.email}}</td>
        <td>{{user.approvalTime | date('Y-m-d H:i')}}</td>
        <td>
            <div class="btn-group">
                <a href="#modal" data-toggle="modal" data-url="{{ path('admin_approval_info_view', {id:user.id}) }}" class="btn btn-default btn-sm">查看</a>

                <a href="#" type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                    <span class="caret"></span>
                </a>

                <ul class="dropdown-menu">
                    <li><a data-url="{{ path('admin_approval_cancel', {id:user.id}) }}" class ="btn cancel-approval">撤销</a></li>
                </ul>
            </div>
        </td>
    </tr>
    {% endfor %}
    </tbody>
</table>

{{ web_macro.paginator(paginator) }}
<#else>
<div class="empty">暂无已经审核成功的实名认证用户</div>
</#if>

</#macro>