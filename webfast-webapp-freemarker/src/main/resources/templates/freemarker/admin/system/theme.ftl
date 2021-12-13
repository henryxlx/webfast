<#assign submenu = 'theme'/>
<#assign script_controller = 'setting/theme'/>

<#include '/admin/system/operation-layout.ftl'/>

<#macro blockTitle>主题设置 - 全局设置 - ${blockTitleParent}</#macro>

<#macro blockMainContent>
<div class="page-header"><h1>主题设置</h1></div>

<table class="table table-striped table-hover" id="theme-table">
    <thead>
    <tr>
        <th>名称</th>
        <th>主题版本</th>
        <th>作者</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#list themes! as theme>
    <tr>
        <td>

            <div class="media">
                <img class="media-object pull-left" src="{{ asset('themes/' ~ theme.uri ~ '/' ~ theme.thumb) }}" width="150">
                <div class="media-body">
                    <h5 class="media-heading">{{ theme.name }}</h5>
                    {{ theme.description|default('')|raw }}
                    {% if theme.setting|default(null) %}
                    <a href="../{{ theme.setting }}">设置</a>
                    {% endif %}
                </div>
            </div>
        </td>
        <td>{{ theme.version }}</td>
        <td>{{ theme.author }}</td>
        <td>
            {% if currentTheme.uri == theme.uri %}
            <span class="text-success">当前正在使用该主题</span>
            {% else %}
            <button class="btn btn-default btn-sm use-theme-btn" data-url="{{ path('admin_setting_theme_change', {uri:theme.uri}) }}">使用</button>
            {% endif %}
        </td>
    </tr>
    </#list>
    </tbody>
</table>

</#macro>