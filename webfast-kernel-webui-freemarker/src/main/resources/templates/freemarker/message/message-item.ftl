<#if message.fromId == appUser.id>
  <#assign messageFrom = 'me' />
<#else>
  <#assign messageFrom = 'she' />
</#if>
<li class="media message-${messageFrom}" parent-url="${ctx}/message">
  <#assign userCssClassName = ' media-object'/>
  <#if messageFrom == 'me'>
    <#assign userCssClassName = 'pull-right' + userCssClassName />
  <#else>
    <#assign userCssClassName = 'pull-left' + userCssClassName />
  </#if>
  <@web_macro.user_avatar message.createdUser!, userCssClassName/>
  <div class="media-body">
    <div class="popover <#if message.fromId == appUser.id >left<#else>right</#if>">
      <div class="arrow"></div>
      <div class="popover-content">
        <div class="message-content">
          <#if messageFrom == 'me'>
            <strong>我：</strong>
          <#else>
            <strong><@web_macro.user_link message.createdUser! />：</strong>
          </#if>
          ${message.content}
        </div>
        <div class="message-footer">
          <span class="text-muted">${message.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm')}</span>
          <div class="message-actions">
            <a class="text-muted delete-message" href="javascript:"
               data-url="${ctx}/message/conversation/${conversation.id}/message/${message.id}/delete"
            >删除</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</li>
