<#assign modalSize = 'large'/>
<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>添加新权限</#macro>

<#macro blockBody>
    <form class="form-horizontal" id="permission-form"
          action="<#if (perm.id)!0 gt 0>${ctx}/admin/permission/${perm.id}/update<#else>${ctx}/admin/permission/create</#if>"
          method="post">
        <div class="form-group">
            <label class="col-md-3 control-label" for="permission-key-field">权限标识关键字</label>
            <div class="col-md-6 controls">
                <input class="form-control" id="permission-key-field" type="text" name="permissionKey"
                       value="${(perm.permissionKey)!}"
                       data-url="${ctx}/admin/permission/checkname?exclude=${(perm.permissionKey)!}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label" for="permission-label-field">权限描述</label>
            <div class="col-md-6 controls">
                <input class="form-control" id="permission-label-field" type="text" name="label"
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