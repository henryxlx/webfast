  <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="nickname">用户名</label>
    </div>
    <div style="margin-top: 7px;">
    ${user.username}
    </div>
  </div>

   <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="truename">真实姓名</label>
    </div>
    <div style="margin-top: 7px;">
      ${userApprovalInfo.truename}
    </div>
  </div>

  <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="idcard">身份证号码</label>
    </div>
    <div style="margin-top: 7px;">
      ${userApprovalInfo.idcard}
    </div>
  </div>

  <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="faceImg">身份证正面照</label>
    </div>
    <div>
     <a href="${ctx}/admin/user/show-idcard?userId=${user.id}&type=face" target="_blank">
     <img src="${ctx}/admin/user/show-idcard?userId=${user.id}&type=face" width="400" />
     </a>
    </div>
  </div>

    <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="faceImg">身份证反面照</label>
    </div>
    <div>
     <a href="${ctx}/admin/user/show-idcard?userId=${user.id}&type=back" target="_blank">
          <img src="${ctx}/admin/user/show-idcard?userId=${user.id}&type=back" width="400" />
      </a>
    </div>
  </div>