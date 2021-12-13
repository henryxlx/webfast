<#assign script_controller = 'message/notification-list'/>

<#include '/layout.ftl'/>
<#macro blockTitle>通知 - ${blockTitleParent}</#macro>
<#macro blockContent>
<div class="row row-8">
    <div class="col-md-8 col-md-offset-2">
        <div class="panel panel-default panel-page">
            <div class="panel-heading">
                <h2> 通知 </h2>
            </div>

            <ul class="media-list notification-list">
                <#if notifications??>
                    <#list notification as notification>
                {% include 'TopxiaWebBundle:Notification:item-' ~ notification.type ~ '.html.twig' %}
                    </#list>
                <#else>
                <li class="empty">暂无通知</li>
                </#if>
            </ul>

            <@web_macro.paginator paginator!/>

        </div><!-- /panel -->
    </div>
</div>

</#macro>