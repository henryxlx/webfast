<tr id="perm-tr-${perm.id}">
    <td>${perm.permissionKey}</td>
    <td>${perm.label}</td>
    <td>${perm.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</td>
    <td>
        <div class="btn-group">
            <#assign appBuildInPermission = dict['permission']!{}/>
            <#assign isAppBuildInPermission = appBuildInPermission?keys?seq_contains(perm.permissionKey)/>
            <button class="btn btn-default btn-sm"
                    data-url="${ctx}/admin/permission/${perm.id}/<#if !isAppBuildInPermission>edit</#if>" data-toggle="modal"
                    data-target="#modal"><#if isAppBuildInPermission>查看<#else>编辑</#if></button>
            <#if !isAppBuildInPermission>
            <a href="#" type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li><a href="javascript:" data-role="delete-item" data-url="${ctx}/admin/permission/${perm.id}/delete">删除权限信息</a>
                </li>
            </ul>
            </#if>
        </div>
    </td>
</tr>