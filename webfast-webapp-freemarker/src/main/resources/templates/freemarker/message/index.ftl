<#assign script_controller = 'message/conversation-list'/>

<#include '/layout.ftl'/>
<#macro blockTitle>私信 - ${blockTitleParent}</#macro>

<#macro blockContent>
<div class="row row-8">
    <div class="col-md-8 col-md-offset-2">
        <div class="panel panel-default panel-page">
            <div class="panel-heading">
                <a class="btn btn-primary pull-right" href="${ctx}/message/send">写私信</a>
                <h2>私信</h2>
            </div>

            <ul class="media-list conversation-list">
                <#if conversations??>
                    <#list conversations as conversation>
                {% set fromUser = users[conversation.fromId] %}
                <li class="media" data-url="{{ path('message_conversation_show', {conversationId:conversation.id })}}">
                    {{ web_macro.user_avatar(fromUser, 'pull-left media-object') }}
                    <div class="media-body">
                        <h4 class="media-heading">
                            <#if conversation.latestMessageUserId == appUser.id >
                            发给
                            </#if>
                            {{ web_macro.user_link(fromUser) }}:
                            {{conversation.latestMessageContent|plain_text(40)}}

                            <#if conversation.unreadNum gt 0>
                            <span class="text-warning">(${conversation.unreadNum}条未读)</span>
                            </#if>
                        </h4>

                        <div class="conversation-footer clearfix">
                            <span class="pull-left">{{ conversation.latestMessageTime|smart_time }}</span>
                            <span class="pull-right">共${conversation.messageNum}条</span>
                            <div class="actions pull-right">
                                <a class="delete-conversation-btn text-muted" href="javascript:" data-url="${ctx}/message/conversation/delete/${conversation.id}">删除</a>
                                <span class="text-muted mhm">|</span>
                            </div>
                        </div>
                    </div>
                </li>
                    </#list>
                <#else>
                <li class="empty">暂无私信</li>
                </#if>
            </ul>

            <@web_macro.paginator paginator!/>

        </div><!-- /panel -->
    </div>
</div>

</#macro>