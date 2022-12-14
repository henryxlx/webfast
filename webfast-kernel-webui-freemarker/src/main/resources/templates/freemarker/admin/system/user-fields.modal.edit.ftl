<div  id="modal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">编辑字段</h4>
      </div>
      <form method="post" class="form-horizontal" id="field-save-form" action="${ctx}/admin/user-fields/edit/${field.id}">
      <div class="modal-body">
            
            <div class="form-group "> 
             <label class="col-md-4 control-label" for="title">字段名称</label>
              <div class="col-md-6 controls">
              <input type="text"  name="title" id="title"  value="${field.title}" class="form-control">
              </div>
            </div>

            <div class="form-group "> 
             <label class="col-md-4 control-label" for="seq">字段类型</label>
              <div class="col-md-6 controls">
              ${field.fieldName}
              </div>
            </div>

            <div class="form-group "> 
             <label class="col-md-4 control-label" for="seq">显示序号</label>
              <div class="col-md-6 controls">
              <input type="text"  name="seq" id="seq" value="${field.seq}" class="form-control">
              </div>
            </div>

            <div class="form-group "> 
             <label class="col-md-4 control-label">是否启用、显示</label>
             <div class="col-md-2"><input type="checkbox"  <#if field.enabled==1 > checked=checked</#if> name="enabled" vaule="1" style="height:18px;width:18px;"></div>
            </div>
            <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
       </div> 
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="submit" class="btn btn-primary" id="save-btn">保存</button>
      </div>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript"> app.load('system/save-modal') </script>