<#assign modalSize = 'large'/>

<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>设置用户组</#macro>

<#macro blockBody>

<form id="user-roles-form" class="form-horizontal" method="post" action="${ctx}/admin/user/${user.id}/roles" data-currentuser="${appUser.id}" data-edituser="${user.id}">
  <div class="checkboxs">
  	<#if userAcl.hasRole('ROLE_SUPER_ADMIN') >
    <@checkboxs 'roles' rolesMap user.roles?split('|') />
    <#else>
    <label>
    	<input type="checkbox" name="roles[]" value="ROLE_USER" <#if user.roles?contains('ROLE_USER')>checked="checked"</#if>>${setting('default.user_name')!'游客'}
    </label>
    <label>
    	<input type="checkbox" name="roles[]" value="ROLE_TEACHER" <#if user.roles?contains('ROLE_TEACHER')>checked="checked"</#if>>教师
    </label>
    <label>
      <input type="checkbox" name="roles[]" value="ROLE_ADMIN" <#if user.roles?contains('ROLE_ADMIN')>checked="checked"</#if>>管理员
    </label>
    </#if>
  </div>
  <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
</form>
</#macro>


<#macro blockFooter>
  <button id="change-user-roles-btn" data-submiting-text="正在提交" class="btn btn-primary pull-right" data-toggle="form-submit" data-target="#user-roles-form"  data-user="${setting('default.user_name')!'游客'}" >保存</button>
  <button type="button" class="btn btn-link pull-right" data-dismiss="modal">取消</button>
  <script>app.load('user/roles-modal')</script>
</#macro>