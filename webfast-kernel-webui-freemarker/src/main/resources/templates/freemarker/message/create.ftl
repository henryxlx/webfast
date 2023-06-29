<#assign script_controller = 'message/create'/>
<#assign script_arguments>{'followingMatchByNickname': "path('following_match_bynickname')" }</#assign>

<@block_title '写私信'/>

<#include '/layout.ftl'/>

<#macro blockContent>
    <style>

        .ui-autocomplete {
            border: 1px solid #ccc;
            background-color: #FFFFFF;
            box-shadow: 2px 2px 3px #EEEEEE;
        }

        .ui-autocomplete-ctn {
    margin:0;
    padding:0;
}
.ui-autocomplete-item {
    width: 180px;
    overflow:hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    line-height: 30px;
    padding:0 10px 0 10px;
    cursor: default;
}
.ui-autocomplete-item-hover {
    background:#f2f2f2;
}
.ui-autocomplete-item-hl {
    background:#F6FF94;
}

</style>
<div class="row row-8">
  <div class="col-md-8 col-md-offset-2">
    <div class="panel panel-default panel-page">
      <div class="panel-heading">
        <a href="${ctx}/message" class="btn btn-default pull-right">返回我的私信</a>
        <h2>写私信</h2>
      </div>

      <form id="message-create-form" class="form-horizontal" method="post">

        <div class="form-group">
          <div class="col-md-12 controls">
              <input type="text" id="message_receiver" name="message[receiver]"
                     required="required" class="form-control" placeholder="收信人昵称"
                     data-auto-url="${ctx}/following/bynickname/match_jsonp"
                     data-url="${ctx}/message/check/receiver" data-display="收信人昵称"  />
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-12 controls">
            <textarea id="message_content" name="message[content]"
                      required="required" class="form-control" rows="5"
                      placeholder="想要说的话" data-display="想要说的话" ></textarea>
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-12 controls">
              <input type="hidden" id="message__token" name="message[_token]" value="31593b6f1ab124ecaa275497bae8622c383f049e" />
            <input id="message-create-btn" class="btn btn-primary pull-right" type="submit" value="发送">
          </div>
        </div>

        <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
      </form>


    </div><!-- /panel -->
  </div>
</div>

</#macro>