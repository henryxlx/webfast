<#assign menu = 'group'/>
<#assign script_controller = 'group/group'/>

<@block_title '小组管理'/>

<#include '/admin/group/layout.ftl'/>

<#macro blockMain>
    <div class="page-header">
        <a href="${ctx}/group/add" class="btn btn-success btn-sm pull-right" target="_blank">创建小组</a>
        <h1>小组管理</h1>
    </div>

    <form id="message-search-form" class="form-inline well well-sm" action="" method="get" novalidate>

    <div class="form-group">

        <select class="form-control" name="status">
            <@select_options dict['groupstatus']!{}, RequestParameters['status']!, '小组状态'/>
        </select>

    </div>

    <div class="form-group">
        <input class="form-control" type="text" placeholder="小组名" name="title" value="${RequestParameters['title']!}">
    </div>

    <div class="form-group">
        <input class="form-control" type="text" placeholder="组长" name="ownerName" value="${RequestParameters['ownerName']!}">
    </div>

    <button class="btn btn-primary">搜索</button>

</form>
<#if groupinfo??>
<table class="table table-striped table-hover" id="group-table">
    <thead>
    <tr>
        <th>编号</th>
        <th width="20%">名称</th>
        <th>组长</th>
        <th>成员数</th>
        <th>话题数</th>
        <th>回复数</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>

    {% for group in groupinfo %}

    {% include 'TopxiaAdminBundle:Group:table-tr.html.twig' with {group:group} %}

    {% endfor %}

    </tbody>

</table>
<#else>
    <div class="empty">暂无小组信息!</div>
</#if>

<div class="pull-right">
    <@web_macro.paginator paginator!/>
</div><div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">请输入用户昵称</h4>
            </div>
            <form  class="form-horizontal" method="post" id="transfer-group-form" >
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-xs-2 control-label" for="user_nickname">转移给</label>
                        <div class="col-xs-7 controls">
                            <input type="text"  id="username" name="user[nickname]" class="form-control"
                                   data-url="{ctx}/group/thread/checkUser" data-display="用户昵称"  placeholder="请输入用户昵称">
                            <div class="help-block" style="display:none;"></div>
                        </div>
                        <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary" >确认转移小组</button>
                </div>
            </form>
        </div>
    </div>
</div>
</#macro>