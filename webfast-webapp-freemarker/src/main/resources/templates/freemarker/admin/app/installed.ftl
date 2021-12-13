<#assign menu = 'installed'/>
<#assign script_controller = 'app/installed'/>

<#include '/admin/app/layout.ftl'/>
<#macro blockTitle>已安装应用 - ${blockTitleParent}</#macro>

<#macro blockMain>
<div class="page-header">
    <h1>已安装应用</h1>
</div>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th width="80%">应用描述</th>
        <th width="10%">开发者</th>
        <th width="10%">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list apps! as app>
    <tr>
        <td>
            <div>
                <a target="_blank" href="http://open.edusoho.com/app/{{ app.code }}">
                    <strong class="text-primary">{{ app.name }} - </strong><span class="text-info">{{ app.version }}</span>
                </a>

            </div>
            <div class="">{{ app.description|plain_text(100)|copyright_less }}</div>
            <div class="text-muted"><small>最后更新： {{ app.updatedTime|date('Y-m-d') }}</small></div>
        </td>
        <td>
            {{ app.developerName|copyright_less }}
        </td>
        <td>
            {% if app.code|lower == 'vip' %}
            <a href="../vip/list" class="btn btn-primary">管理</a>
            {% elseif app.code|lower == 'coupon' %}
            <a href="../coupon" class="btn btn-primary">管理</a>
            {% elseif app.code|lower == 'questionplus' %}
            <a href="../question/plumber/setting" class="btn btn-primary">管理</a>
            {% elseif app.code|lower in ['homework', 'materiallib'] %}
            {% elseif app.code|lower != 'main' and app.type == 'plugin' %}
            <a href="../{{ app.code|lower }}/index" class="btn btn-primary">管理</a>
            {% endif %}

            {% if setting('developer.debug') and app.code|lower != 'main' %}
            <a href="javascript:;" data-url="{{ path('admin_app_uninstall', {code:app.code}) }}" class="btn btn-danger uninstall-btn">卸载</a>
            {% endif %}
        </td>
    </tr>
    </#list>
    </tbody>
</table>

</#macro>