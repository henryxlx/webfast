<#assign menu = 'article'/>
<#assign script_controller = 'article/list'/>

<#include '/admin/content/layout.ftl'/>
<#macro blockTitle>资讯管理 - ${blockTitleParent}</#macro>

<#macro blockMain>

<div class="page-header clearfix">
    <a class="btn btn-success btn-sm pull-right" id="article-add-btn" type="button" href="${ctx}/admin/article/create"><span class="glyphicon glyphicon-plus"></span> 添加资讯</a>
    <h1 class="pull-left">资讯管理</h1>
</div>

<form class="well well-sm form-inline" action="${ctx}/admin/article">
    <div class="form-group">
        <select class="form-control" name="categoryId">
            <option value="">--所属栏目--</option>
            <#list categoryTree! as tree>
            <option value=${tree.id} <#if tree.id == categoryId>selected</#if>><#list 0..(tree.depth-1)*2 as i>&nbsp;&nbsp;&nbsp;&nbsp;</#list>└─ ${tree.name}</option>
            </#list>
        </select>
    </div>
    <div class="form-group">
        <input class="form-control" name="keywords" type="text" placeholder="标题关键词" value="${RequestParameters['keywords']!}">
    </div>
    <div class="form-group">
        <select class="form-control" name="property">
            <@select_options dict['articleProperty']!{}, RequestParameters['property']!, '--属性--' />
        </select>
    </div>
    <div class="form-group">
        <select class="form-control" name="status">
            <@select_options dict['articleStatus']!{}, RequestParameters['status']!, '--发布状态--' />
        </select>
    </div>
    <button class="btn btn-primary" type="submit">搜索</button>
</form>

<div id="aticle-table-container">

    <table class="table table-hover table-striped" id="article-table">
        <thead>
        <tr>
            <th width="10%"><input type="checkbox"  data-role="batch-select"> ID</th>
            <th width="20%">资讯标题</th>
            <th width="15%">栏目</th>
            <th width="18%">更新时间</th>
            <th width="15%">属性</th>
            <th width="10%">状态</th>
            <th width="15%">操作</th>
        </tr>
        </thead>
        <tbody>
        <#list articles! as article>
            <#assign category = categories[article.categoryId + '']! />
            <#include '/admin/article/article-tr.ftl' />
        <#else>
        <tr><td colspan="20"><div class="empty">暂无页面记录</div></td></tr>
        </#list>
        </tbody>
    </table>
    <div>
        <label class="checkbox-inline"><input type="checkbox" data-role="batch-select"> 全选</label>
        <button class="btn btn-default btn-sm mlm" data-role="batch-delete"  data-name="资讯" data-url="${ctx}/admin/article/delete">删除</button>
    </div>
</div>

<@web_macro.paginator paginator! />

</#macro>