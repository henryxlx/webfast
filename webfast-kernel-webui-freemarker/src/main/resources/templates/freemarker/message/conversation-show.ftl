<#assign script_controller = 'message/show'/>

<@block_title '私信'/>

<#include '/layout.ftl'/>

<#macro blockContent>

    <div class="row row-8">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default panel-page">
                <div class="panel-heading">
                    <a href="${ctx}/message" class="btn btn-default pull-right">返回我的私信</a>
                    <h2>与${receiver.username}的私信对话</h2>
                </div>

                <form id="message-reply-form" class="message-reply-form clearfix" method="post"
                      action="${ctx}/message/conversation/${conversation.id}">
                    <div class="form-group">
          <textarea id="message_reply_content" name="content"
                    required="required" class="form-control" rows="3"
                    placeholder="请输入私信内容" data-display="请输入私信内容"></textarea>
                    </div>

                    <div class="form-group">
                        <button id="course-reply-btn" class="btn btn-primary pull-right disabled">发送</button>
                    </div>

                    <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
                </form>

                <ul class="media-list message-list">
                    <#list messages as message>
                        <#include '/message/message-item.ftl' />
                    </#list>
                </ul>

                <@web_macro.paginator paginator! />

            </div><!-- /panel -->
        </div>
    </div>


</#macro>