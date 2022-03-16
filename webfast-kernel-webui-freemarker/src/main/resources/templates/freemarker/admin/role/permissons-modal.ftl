<#assign modalSize = 'large'/>

<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>设置角色权限</#macro>

<#macro blockBody>

<form id="role-permissions-form" class="form-horizontal" method="post" action="${ctx}/admin/role/${role.id}/permissions" data-currentuser="${appUser.id}" data-edituser="${role.id}">
  <div class="checkboxs">
  	<#list permissions as perm>
    <label>
    	<input type="checkbox"
               <#if role.permissionData?contains(perm.permissionKey)>checked="checked"</#if>
               name="permissions[]" value="${perm.permissionKey}" >${perm.label}
    </label>

    </#list>
  </div>
  <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
</form>
</#macro>


<#macro blockFooter>
  <button id="change-role-permissions-btn" data-submiting-text="正在提交" class="btn btn-primary pull-right" data-toggle="form-submit" data-target="#role-permissions-form"  data-user="${setting('default.user_name')!'游客'}" >保存</button>
  <button type="button" class="btn btn-link pull-right" data-dismiss="modal">取消</button>
  <script>app.load('role/permissions-modal')</script>
</#macro>