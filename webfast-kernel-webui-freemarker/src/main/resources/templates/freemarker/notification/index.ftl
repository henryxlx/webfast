<#assign script_controller = 'message/notification-list'/>

<#include '/layout.ftl'/>
<#macro blockTitle>通知 - ${blockTitleParent}</#macro>
<#macro blockContent>
    <div class="row row-8">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default panel-page">
                <div class="panel-heading">
                    <a class="btn btn-default pull-right" href="${ctx}/notification/${appUser.id}/clearAll">清除全部通知</a>
                    <h2> 通知 </h2>
                </div>

                <ul class="media-list notification-list">
                    <#list notifications! as notification>
                        <#include "/notification/item-${notification.type}.ftl" />
                    <#else>
                        <li class="empty">暂无通知</li>
                    </#list>
                </ul>

            <@web_macro.paginator paginator!/>

        </div><!-- /panel -->
    </div>
</div>

</#macro>