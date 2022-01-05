<#assign modal_class = 'modal-lg'/>
<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>编辑内容</#macro>

<#macro blockBody>

<h5>编辑区名称：${block.title}</h5>

<form id="block-form"  <#if block.mode == 'template'>class="form-horizontal" </#if> action="${ctx}/admin/block/${block.id}/update" method="post">
  <#if block.mode == 'html'>
  <div class="form-group">
      <div class="controls">
        <textarea class="form-control" id="blockContent" rows="10" name="content" data-role="editor-field" style="z-index:1000;">${block.content!}</textarea>
      </div>
  </div>
  <#else>
    <#list templateItems! as item>
      {% set join = [item.title,item.type]|join(':') %}
      {% set template = templateData[join]|default(null) %}
      <#if item.type == 'a' || item.type == 'text'>
        <div class="form-group">
          <div class="col-md-2 control-label"><label for="{{ item.title }}:{{ item.type }}">{{ item.title }}</label></div>
          <div class="col-md-8 controls"> 
          <input class="form-control" type="text" id="{{ item.title }}:{{ item.type }}" value="{{ template }}" name="{{ item.title }}:{{ item.type }}"></div>
        </div>
      <#else>
        <div class="form-group">
          <div class="col-md-2 control-label"><label for="{{ item.title }}:{{ item.type }}">{{ item.title }}</label></div>
          <div class="col-md-8 controls">
            <button class="btn btn-default btn-sm upload-img" id="{{ item.title}}:{{ item.type }}" type="button" data-url="{{ path('file_upload', {group:'default'}) }}" data-name="{{ item.title}}:{{ item.type }}" >上传</button>       
            <button class="btn btn-default btn-sm upload-img-del" type="button"  data-name="{{ item.title}}:{{ item.type }}" {% if not template %}style="display:none;"{% endif %}>删除</button>
            <input type="hidden" name="{{ item.title }}:{{ item.type }}" value="{{ template }}">
            <a href="{{ template }}" target="_blank" {% if not template %}style="display:none;"{% endif %}>{{ item.title }}</a>
          </div>
        </div>
      </#if>
    </#list>
  </#if>
  <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
</form>
<#if block.mode == 'html'>
<form id="block-image-upload-form" action="{{ path('file_upload', {group:'default'}) }}" method="post" enctype="multipart/form-data">
  <input class="btn btn-default btn-sm" type="file" name="file" value="上传" style="display:inline-block;">
  <button class="btn btn-default btn-sm">上传图片</button>
  <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
</form>
</#if>

<div class="mbm mtl"><strong>编辑帮助</strong></div>
<#-- 将block.tips中的回车与换行替换成HTML中的标签<br/>，注意replace函数中的第三个参数是说明被替换的内容是正则表达式，否则replace不起作用 -->
<div class="text-info">${block.tips!''?replace("(\r\n)+", "<br/>", 'r')}</div>

<div class="mbm mtl"><strong>变更记录</strong></div>
  <table id="block-history-table" class="table table-striped table-condensed">
    <tbody>
      <#list blockHistories! as blockHistory>
      <#local templateData = blockHistory.templateData/>
        <tr id="${blockHistory.id}">
          <td>${blockHistory.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</td>
          <td>${(historyUsers[blockHistory.userId?c].username)!}</td>
          <td>
            <a class="btn btn-xs btn-default edit-btn" 
            href="${ctx}/admin/blockhistory/${blockHistory.id}/preview" target="_blank"> 预览 </a>
          </td>
          <#if block.mode == 'template'>
          <td>
            <button class="btn-recover-template btn btn-xs btn-default edit-btn">恢复此内容</button>
            <div class="data-role-content" style="display:none">{templateData|json_encode() }}</div>
          </td>
          <#else>
          <td>
            <button class="btn-recover-content btn btn-xs btn-default edit-btn">恢复此内容</button>
            <div class="data-role-content" style="display:none"> ${blockHistory.content!} </div>
          </td>
          </#if>
        </tr>
      </#list>
    </tbody>
  </table>
   <@web_macro.paginator paginator! />
</#macro>

<#macro blockFooter>
  <button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
  <button id="block-update-btn" data-submiting-text="正在提交" type="submit" class="btn btn-primary" data-toggle="form-submit" data-target="#block-form">保存</button>
  <script type="text/javascript">app.load('block/update')</script>
</#macro>
