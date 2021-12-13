<#assign menu = 'logs'/>
<#assign script_controller = 'log/list'/>

<#include '/admin/system/layout.ftl'/>
<#macro blockTitle>系统日志 - ${blockTitleParent}</#macro>

<#macro blockMain>

<div class="page-header"><h1>系统日志</h1></div>

<@web_macro.flash_messages />

<form class="well well-sm form-inline">

  <div class="form-group">
    <select class="form-control" name="level">
        <@select_options dict['logLevel']!{} RequestParameters['level']! '--所有等级--' />
    </select>
  </div>

  <div class="form-group">
    <input type="text" id="startDateTime" value="${RequestParameters['startDateTime']!}" name="startDateTime" class="form-control" placeholder="起始时间" style="width:150px;">
  </div>

  <div class="form-group">
    <input type="text" id="endDateTime" value="${RequestParameters['endDateTime']!}" name="endDateTime" class="form-control" placeholder="结束时间" style="width:150px;">
  </div>

  <div class="form-group">
    <input type="text" id="module" name="module" value="${RequestParameters['module']!}" class="form-control" placeholder="模块名" style="width:100px;">
  </div>

  <div class="form-group">
    <input type="text" id="action" name="action" value="${RequestParameters['action']!}" class="form-control" placeholder="操作名" style="width:100px;">
  </div>

  <div class="form-group">
    <input type="text" id="nickname" name="nickname" value="${RequestParameters['nickname']!}" class="form-control" placeholder="用户昵称" style="width:120px;">
  </div>

  <button class="btn btn-primary">搜索</button>

</form>

  <table class="table table-hover" id="log-table">
    <tr>
      <th width="15%">用户</th>
      <th width="55%">操作</th>
      <th width="10%">日志等级</th>
      <th width="20%">时间/IP</th>
    </tr>   
    <#list logs! as log>
      {% set user = users[log.userId]|default(null) %}
      <tr>
        <td>
          {% if user %}
            {{ admin_macro.user_link(user) }}
          {% else %}
            --
          {% endif %}
        <td>
          <div style="word-break: break-all;word-wrap: break-word;">
            {{ log.message }}
            {% if log.data %}
              <a href="javascript:;" class="text-sm text-warning show-data">查看数据</a>
              <a href="javascript:;" class="text-sm text-warning hide-data" style="display:none;">隐藏数据</a>
              <div class="data" style="display:none;">{{ log.data|json_encode }}</div>
            {% endif %}
          </div>
          <span class="text-muted text-sm">{{ log.module }}.{{ log.action }}</span>
        </td>
        <td>{{ dict('logLevel:html')[log.level]|raw }}</td>
        <td>
          <span class="">{{ log.createdTime|date("Y-m-d H:i:s") }}</span>
          <br />
          <a  class="text-muted text-sm" href="http://www.baidu.com/s?wd={{ log.ip }}" target="_blank">{{ log.ip }}</a>
        </td>
      </tr>
    <#else>
      <tr><td class="empty" colspan="20">无日志记录</tr>
    </#list>
  </table>

  <@web_macro.paginator paginator! />

</#macro>