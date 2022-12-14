<#assign modalSize = 'large'/>
<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>添加新角色</#macro>

<#macro blockBody>
    <form class="form-horizontal" id="role-form"
          action="${ctx}/admin/role/<#if (role.id)?? && role.id gt 0>${role.id}/update<#else>create</#if>"
          method="post">
        <div class="form-group">
            <label class="col-md-3 control-label" for="role-name-field">角色编码</label>
            <div class="col-md-6 controls">
                <input class="form-control" id="role-name-field" type="text"
                       <#if dataReadOnly??>readOnly="true"</#if>
                       name="roleName" value="${(role.roleName)!}"
                       data-url="${ctx}/admin/role/checkname?exclude=${(role.roleName)!}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label" for="role-label-field">角色名描述</label>
            <div class="col-md-6 controls">
                <input class="form-control" id="role-label-field" type="text"
                       <#if dataReadOnly??>readOnly="true"</#if>
                       name="label" value="${(role.label)!}">
            </div>
        </div>
        <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
    </form>
    <script type="text/javascript">app.load('role/save-modal')</script>
</#macro>

<#macro blockFooter>
    <button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
    <#if !dataReadOnly??>
        <button id="role-create-btn" data-submiting-text="正在提交" type="submit" class="btn btn-primary"
                data-toggle="form-submit" data-target="#role-form">保存</button>
    </#if>
</#macro>