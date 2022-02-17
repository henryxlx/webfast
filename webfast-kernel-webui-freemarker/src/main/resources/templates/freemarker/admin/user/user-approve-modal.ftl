<#assign modalSize = 'large' />
<#include '/bootstrap-modal-layout.ftl' />

<#macro blockTitle>用户认证审核</#macro>

<#macro blockBody>

<form id="approve-form" class="form-horizontal" method="post" action="${ctx}/admin/approval/${user.id}/approve">

  <#include '/admin/user/user-approval-form-content.ftl' />

  <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="note">备注</label>
    </div>
    <div class="col-md-8">
    <textarea id="note" name="note" class="form-control" rows="3"></textarea>
    </div>
</div>


  <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
  <input id="form_status" type="hidden" name="form_status" >
</form>
</#macro>

<#macro blockFooter>
<div class="pull-right">

  <button type="button" class="btn btn-link" data-dismiss="modal">取消</button>


  <button type="submit" class="btn btn-danger user-approve-btn" data-toggle="form-submit" data-status='fail' data-target="#approve-form">审核失败</button>
  <button type="submit" class="btn btn-success user-approve-btn" data-toggle="form-submit" data-status='success' data-target="#approve-form">审核成功</button>


  <script>app.load('user/approve')</script>
    
</div>
</#macro>