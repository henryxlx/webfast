<#assign menu = 'message'/>
<#assign script_controller = 'message/list'/>

<@block_title '私信管理'/>
<#include '/admin/user/layout.ftl'/>

<#macro blockMain>
<div class="page-header">
    <h1>私信管理</h1>
</div>
<div class="well well-sm">
    <form id="message-search-form" class="form-inline" action="" method="get" novalidate>

        <div class="form-group">
            <input class="form-control" type="text" id="startDate" name="startDate" value="${RequestParameters['startDate']!}" placeholder="起始时间">
        </div>

        <div class="form-group">
            <input class="form-control" type="text" id="endDate" name="endDate" value="${RequestParameters['endDate']!}" placeholder="结束时间">
        </div>
        <span></span>
        <div class="form-group">
            <input type="text" id="nickname" name="nickname" value="${RequestParameters['nickname']!}" class="form-control" placeholder="发信人昵称">
        </div>

        <div class="form-group">
            <input type="text" id="content" name="content" value="${RequestParameters['content']!}" class="form-control" placeholder="私信内容关键词">
        </div>

        <button class="btn btn-primary">搜索</button>
    </form>
</div>

<div id="message-table-container">

    <table class="table table-striped table-hover" id="message-table">
        <thead>
        <tr >
            <th width="10%"> <input type="checkbox"  data-role="batch-select"> 发信人</th>
            <th width="10%">收信人</th>
            <th width="45%">内容</th>
            <th width="15%">发送时间</th>
        </tr>
        </thead>
        <tbody>
        <#list messages as message>
        <tr class="message-tr" id="message-table-tr-${message.id}">
            <td><input value="${message.id}" type="checkbox" data-role="batch-item" >
                <@admin_macro.user_link users['' + message.fromId] />
            </td>

            <td><@admin_macro.user_link users['' + message.toId] /> </td>
            <td>
                <div class="short-long-text">
                    <#assign contentLength = message.content?length - 1 />
                    <#if contentLength gt 60>
                        <#assign contentLength = 60 />
                    </#if>
                    <div class="short-text text-sm text-muted">${message.content[0..contentLength]} <span class="trigger">(展开)</span></div>
                    <div class="long-text">${message.content} <span class="trigger">(收起)</span></div>
                </div>
            </td>
            <td>${message.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</td>
        </tr>
        <#else>
        <tr><td colspan="20"><div class="empty">暂无私信记录</div></td></tr>
        </#list>

        </tbody>
    </table>
    <div>
        <label class="checkbox-inline"><input type="checkbox" data-role="batch-select"> 全选</label>
        <button class="btn btn-default btn-sm mlm" data-role="batch-delete"  data-name="私信" data-url="${ctx}/admin/message/delete-many">删除</button>
    </div>

</div>

<div>
    <@web_macro.paginator paginator! />
</div>

</#macro>