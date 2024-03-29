<#assign menu = 'thread'/>
<#assign script_controller = 'group/thread'/>

<@block_title '话题管理'/>

<#include '/admin/group/layout.ftl'/>

<#macro blockMain>

    <div class="page-header">
        <h1>话题管理</h1>
    </div>

    <form class="form-inline well well-sm" action="" method="get" novalidate>

    <div class="form-group">
      <select class="form-control" name="status">
        <@select_options dict['groupstatus']! RequestParameters['status']!, '话题状态' />
      </select>
    </div>
    
    <div class="form-group">
      <select class="form-control" name="threadType">
        <@select_options dict['threadProperty']! RequestParameters['threadType']!, '属性' />
      </select>
    </div>

    <div class="form-group">
      <input class="form-control" type="text" placeholder="所属小组" name="groupName" value="${RequestParameters['groupName']!}">
    </div>

    <div class="form-group">
      <input class="form-control" type="text" placeholder="话题名称关键词" name="title" value="${RequestParameters['title']!}">
    </div>

    <div class="form-group">
      <input class="form-control" type="text" placeholder="创建者" name="userName" value="${RequestParameters['userName']!}">
    </div>
    
    <button class="btn btn-primary">搜索</button>
  </form>
  <#if threadinfo??>
  <form method="post" id="thread-form">

      <table class="table table-striped table-hover" id="thread-table">

        <thead>
        <tr>
          <th width="7%"><input type="checkbox"  data-role="batch-select"/> 编号</th>
          <th width="30%">名称</th>
          <th width="10%">属性</th>
          <th width="10%">创建者</th>
          <th width="16%">所属小组</th>
          <th width="7%">回复数</th>
          <th width="10%">状态</th>
          <th width="10%">操作</th>
        </tr>
        </thead>

        <tbody>
          {% for thread in threadinfo %}  
          {% include 'TopxiaAdminBundle:Group:thread-table-tr.html.twig' with {thread:thread} %}
        {% endfor %}
        </tbody>

        <tr>  

          <td><label class="checkbox-inline"><input type="checkbox" data-role="batch-select"> 全选</label>
          </td> 

          <td>   
           <input type="hidden" id="batchDeleteThread" value="{{path('admin_groupThread_batch_delete')}}">
           
           <button id="thread-delete-btn" data-submiting-text="正在删除" type="button" class="btn btn-default btn-sm"
           >删除话题</button>
 
          </td>

        </tr>

       </table>

       <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">

   </form>
   <#else>
      <div class="empty">暂无话题！</div>
   </#if>
   <div class="pull-right">
 <@web_macro.paginator paginator! />
</div>
</#macro>