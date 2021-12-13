<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>
    <#if navigation??>
        <#if navigation.id == 0 && navigation.type =='top'>
        新增顶部导航
        <#elseif navigation.id gt 0 && navigation.type =='top'>
        编辑顶部导航
        <#elseif navigation.id == 0 && navigation.type =='foot'>
        新增底部导航
        <#elseif navigation.id gt 0 && navigation.type =='foot'>
        编辑底部导航
        </#if>
    </#if>
</#macro>

<#macro blockBody>

  <form class="form-horizontal" id="navigation-form"
  <#if (navigation.id)?? && navigation.id gt 0>
    action="${ctx}/admin/navigation/${navigation.id}/update"
  <#else>
    action="${ctx}/admin/navigation/create"
  </#if>
 method="post">

  <#if parentNavigation??>
  <div class="row form-group">
    <div class="col-md-3 control-label"><label>上级导航</label></div>
    <div class="col-md-8 controls"> 
      ${parentNavigation.name}
      <div class="help-block">请注意：增加子导航后，对应的上级导航链接将不起作用。</div>
    </div>
  </div>
  </#if>


 <div class="row form-group">
  <div class="col-md-3 control-label"><label for="name">名称</label></div>
  <div class="col-md-8 controls"> 
    <input class="form-control" type="text" id="name" value="${(navigation.name)!}" name="name"></div>
</div>
<p></p>

<div class="row form-group">
  <div class="col-md-3 control-label"><label for="url">链接地址</label></div>
  <div class="col-md-8 controls"> <input class="form-control" type="text" id="url" value="${(navigation.url)!'http://'}" name="url" ></div>
</div>

<p></p>

<input type="hidden" name="type" value="${(navigation.type)!}" >

<div class="row form-group">
          <div class="col-md-3 control-label"><label>新开窗口</label></div>
          <div class="col-md-8 controls radios"> 
            <div id="isNewWin">
              <input type="radio" name="isNewWin"  value="0"
               <#if navigation.isNewWin == 0> checked="checked" </#if>>
              <label >否</label>
              <input type="radio" name="isNewWin"  value="1"
               <#if navigation.isNewWin == 1> checked="checked" </#if>>
              <label >是</label>
            </div>
          </div>
</div>

<div class="row form-group">
      <div class="col-md-3 control-label">
        <label>状态</label>
      </div>
      <div class="col-md-8 controls radios">
        <div id="isOpen">
          <input type="radio" name="isOpen"  value="1" 
           <#if navigation.isOpen == 1> checked="checked" </#if>>
          <label>开启</label>
          <input type="radio" name="isOpen"  value="0"
           <#if navigation.isOpen == 0> checked="checked" </#if>>
          <label>关闭</label>
        </div>
      </div>
</div>
<input type="hidden" name="parentId" value="${(parentNavigation.id)!0}">
<input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">

  </form>

  <script type="text/javascript">
  <#if (navigation.id)?? && navigation.id gt 0>
    app.load('navigation/edit-modal')
  <#else>
    app.load('navigation/create-modal')
  </#if>
  </script>
  
</#macro>

<#macro blockFooter>
  <button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
  <button id="navigation-save-btn" data-submiting-text="正在提交" type="submit" class="btn btn-primary" data-toggle="form-submit" data-target="#navigation-form">保存</button>
</#macro>
