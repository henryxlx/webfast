<#assign modalSize = 'large'/>
<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>添加新权限</#macro>

<#macro blockBody>
    <form class="form-horizontal" id="permission-form"
          action="${ctx}/admin/permission/<#if (perm.id)?? && perm.id gt 0>${perm.id}/update<#else>create</#if>"
          method="post">
        <div class="form-group">
            <label class="col-md-3 control-label" for="permission-key-field">权限标识</label>
            <div class="col-md-6 controls">
                <input class="form-control" id="permission-key-field" type="text" name="permissionKey"
                       <#if dataReadOnly??>readOnly="true"</#if>
                       value="${(perm.permissionKey)!}"
                       data-url="${ctx}/admin/permission/checkname?exclude=${(perm.permissionKey)!}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label" for="permission-label-field">权限描述</label>
            <div class="col-md-6 controls">
                <input class="form-control" id="permission-label-field" type="text" name="label"
                       <#if dataReadOnly??>readOnly="true"</#if>
                       value="${(perm.label)!}">
            </div>
        </div>
        <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
    </form>
    <script type="text/javascript">app.load('permission/save-modal')</script>
</#macro>

<#macro blockFooter>
    <button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
    <button id="permission-create-btn" data-submiting-text="正在提交" type="submit" class="btn btn-primary"
            data-toggle="form-submit" data-target="#permission-form">保存</button>
</#macro>