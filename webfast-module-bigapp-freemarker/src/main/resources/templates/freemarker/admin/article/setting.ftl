<#assign menu = 'setting'/>

<@block_title '资讯频道设置'/>

<#include '/admin/content/layout.ftl'/>

<#macro blockMain>

    <div class="page-header clearfix">
        <h1 class="pull-left">资讯频道设置</h1>
    </div>

    <@web_macro.flash_messages />

  <form class="form-horizontal" id="article-form" method="post">
    
    <div class="form-group">
      <div class="col-md-2 control-label">
        <label for="name">频道名称</label>
      </div>
      <div class="col-md-8 controls">
        <input type="text" id="name" name="name" class="form-control" value="${(articleSetting.name)!}">
      </div>
    </div>

    <div class="form-group">
      <div class="col-md-2 control-label">
        <label for="pageNums">列表每页资讯数</label> 
      </div>
      <div class="col-md-8 controls">
        <input type="text" id="pageNums" name="pageNums" class="form-control" value="${(articleSetting.pageNums)!}">
      </div>
    </div>

    <div class="form-group">
      <div class="col-md-2 control-label"></div>
      <div class="controls col-md-8">
        <button type="submit" class="btn btn-primary">提交</button>  
      </div>
    </div>

    <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
  </form>

</#macro>