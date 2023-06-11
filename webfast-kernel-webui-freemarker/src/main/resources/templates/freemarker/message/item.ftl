<li class="media message-me" parent-url="${ctx}/message">

    <@web_macro.user_avatar appUser, 'pull-right' />

    <div class="media-body">
        <div class="popover left">
            <div class="arrow"></div>
            <div class="popover-content">
                <div class="message-content">
                    <strong>我：</strong>
                    ${message.content!}
                </div>
                <div class="message-footer">
                    <span class="text-muted">${message.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm')}</span>
                    <div class="message-actions">
                        <a class="text-muted delete-message" href="javascript:"
                           data-url="${ctx}/message/conversation/${conversation.id}/message/${message.id}/delete">删除</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</li>