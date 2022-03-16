<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>
<#if tag.id??>编辑<#else>新增</#if>标签
</#macro>
<#macro blockBody>
  <form class="form-horizontal" id="tag-form" action="${ctx}/admin/tag/<#if tag.id??>${tag.id}/update<#else>create</#if>" method="post">
    <div class="form-group">
      <label class="col-md-3 control-label" for="tag-name-field">标签名称</label>
      <div class="col-md-6 controls">
        <input class="form-control" id="tag-name-field" type="text" name="name" value="${tag.name}" data-url="${ctx}/admin/tag/checkname?exclude=${tag.name}">
      </div>
    </div>
    <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
  </form>
  <script type="text/javascript">app.load('tag/save-modal')</script>
</#macro>

<#macro blockFooter>
  <#if tag.id??>
    <button class="btn btn-default pull-left delete-tag" data-url="${ctx}/admin/tag/${tag.id}/delete" data-target="${tag.id}" data-tag-id="${tag.id}"><i class="glyphicon glyphicon-trash"></i> 删除</button>
  </#if>

  <button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
  <button id="tag-create-btn" data-submiting-text="正在提交" type="submit" class="btn btn-primary" data-toggle="form-submit" data-target="#tag-form">保存</button>
</#macro>
