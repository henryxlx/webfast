<#assign hideFooter = true />
<#include '/bootstrap-modal-layout.ftl' />

<#macro blockTitle>发送私信</#macro>

<#macro blockBody>
  <form id="message-create-form" class="form-horizontal" method="post" action="${ctx}/message/create/${userId}">

    <div class="form-group">
      <div class="col-md-2 control-label"><label for="message_receiver">收件人</label></div>
      <div class="col-md-10 controls">
        <input type="text" id="message_receiver" name="message[receiver]"
               value="${receiver}"
               required="required" class="form-control" placeholder="收信人昵称"
               data-url="${ctx}/message/check/receiver" data-display="收信人昵称"/>
      </div>
    </div>

    <div class="form-group">
      <div class="col-md-2 control-label"><label for="message_content">内容</label></div>
      <div class="col-md-10 controls">
        <textarea id="message_content" name="message[content]"
                  required="required" class="form-control" rows="5"
                  placeholder="想要说的话" data-display="想要说的话"></textarea>
      </div>
    </div>

    <div class="form-group">
      <div class="col-md-offset-2 col-md-10 controls">
        <button class="btn btn-primary" type="submit">发送</button>
      </div>
    </div>

    <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
  </form>

  <script> app.load('user/message-send-modal'); </script>
</#macro>
