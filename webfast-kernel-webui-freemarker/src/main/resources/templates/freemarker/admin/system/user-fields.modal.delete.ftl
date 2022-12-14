<div  id="modal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">删除字段</h4>
      </div>
      <form method="post" class="form-horizontal" id="field-save-form" action="${ctx}/admin/user-fields/delete/${field.id}">
      <div class="modal-body">
            
            <div class="form-group ">
             <label class="col-md-10 col-md-offset-2" for=
             "title">确定要删除 <b style="color:red;">${field.title} </b>字段吗？<br><b style="color:red;">所有该字段的历史数据都将一起删除！</b><br>如要保留历史数据，请隐藏该字段</label>
            </div>
            <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
       </div> 
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="submit" class="btn btn-primary" id="save-btn">确定删除</button>
      </div>
      </form>
    </div>
  </div>
</div>
