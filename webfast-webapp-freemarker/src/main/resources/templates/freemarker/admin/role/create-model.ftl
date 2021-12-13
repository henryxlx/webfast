<#assign modalSize = 'large'/>
<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>添加新角色</#macro>

<#macro blockBody>
        <form class="form-horizontal" id="role-form" action="<#if (role.id)!0 gt 0>${ctx}/admin/role/update/${role.id}<#else>${ctx}/admin/role/create</#if>" method="post">
            <div class="form-group">
                <label class="col-md-3 control-label" for="role-name-field">角色名称</label>
                <div class="col-md-6 controls">
                    <input class="form-control" id="role-name-field" type="text" name="name" value="${(role.roleName)!}" data-url="${ctx}/admin/role/checkname?exclude=${(role.roleName)!}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label" for="role-description-field">角色描述</label>
                <div class="col-md-6 controls">
                    <input class="form-control" id="role-description-field" type="text" name="description" value="${(role.description)!}">
                </div>
            </div>
            <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
        </form>
        <script type="text/javascript">app.load('role/save-modal')</script>
</#macro>

<#macro blockFooter>
        <#if (role.id)!0 gt 0>
        <button class="btn btn-default pull-left delete-role" data-url="${ctx}/admin/role/delete/${(role.id)!}" data-target="${(role.id)!}" data-role-id="${(role.id)!}"><i class="glyphicon glyphicon-trash"></i> 删除</button>
        </#if>

        <button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
        <button id="role-create-btn" data-submiting-text="正在提交" type="submit" class="btn btn-primary" data-toggle="form-submit" data-target="#role-form">保存</button>
</#macro>