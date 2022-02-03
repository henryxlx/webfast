<#assign modalSize = 'large' />
<#include '/bootstrap-modal-layout.ftl' />

<#macro blockTitle>用户认证查看</#macro>

<#macro blockBody>
  <div class="form-horizontal">
  <#include '/admin/user/user-approval-form-content.ftl' />
  </div>
</#macro>

<#macro blockFooter>
  <div class="pull-right">
    <button type="button" class="btn btn-primary " data-dismiss="modal">关闭</button>    
  </div>
</#macro>