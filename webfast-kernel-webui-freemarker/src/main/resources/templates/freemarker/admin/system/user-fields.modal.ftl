<div class="modal fade" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">添加字段</h4>
      </div>
      <form method="post" class="form-horizontal" id="field-form" action="${ctx}/admin/user-fields/add">
      <div class="modal-body">
            
            <div class="form-group "> 
             <label class="col-md-4 control-label" for=
             "field_title">字段名称</label>
              <div class="col-md-6 controls">
              <input type="text"  name="field_title" id="field_title"  class="form-control" value="">
              </div>
            </div>

            <div class="form-group "> 
             <label class="col-md-4 control-label" for="field_type">字段类型</label>
              <div class="col-md-6 controls">
                  <select class="form-control" name="field_type" id="field_type">
                    <option value="" num="">请选择</option>
                    <option value ="varchar" num="还可以添加${10-varcharCount!10}个字段" <#if varcharCount?? && varcharCount==10>disabled="disabled"</#if>>文本<#if varcharCount?? && varcharCount==10>(已满10个)</#if></option>
                    <option value ="text" num="还可以添加${10-textCount!10}个字段" <#if textCount?? && textCount==10>disabled="disabled"</#if>>多行文本<#if textCount?? && textCount==10>(已满10个)</#if></option>
                    <option value="int" num="还可以添加${5-intCount!5}个字段,最大值为9位整数" <#if intCount?? && intCount==5>disabled="disabled"</#if>>整数<#if intCount?? && intCount==5>(已满5个)</#if></option>
                    <option value="float" num="还可以添加${5-floatCount!5}个字段,保留到两位小数" <#if floatCount?? && floatCount==5>disabled="disabled"</#if>>小数<#if floatCount?? && floatCount==5>(已满5个)</#if></option>
                    <option value="date" num="还可以添加${5-dateCount!5}个字段" <#if dateCount?? && dateCount==5>disabled="disabled"</#if>>日期<#if dateCount?? && dateCount==5>(已满5个)</#if></option>
                  </select>
                  <div class="help-block" id="type_num" style="color:green;" ></div>
              </div>
            </div> 

            <div class="form-group "> 
             <label class="col-md-4 control-label" for="field_seq">显示序号</label>
              <div class="col-md-6 controls">
              <input type="text"  name="field_seq" id="field_seq" class="form-control" value="1">
              </div>
            </div>

            <div class="form-group "> 
             <label class="col-md-4 control-label">是否启用、显示</label>
             <div class="col-md-2"><input type="checkbox"  checked=checked name="field_enabled" vaule="1" style="height:18px;width:18px;"></div>
            </div>
            <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
       </div> 
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="submit" class="btn btn-primary" id="add-btn">保存</button>
      </div>
      </form>
    </div>
  </div>
</div>