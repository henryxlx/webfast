<#assign menu = 'set'/>

<@block_title '小组设置'/>

<#include '/admin/group/layout.ftl'/>

<#macro blockMain>
    <@web_macro.flash_messages />

    <div class="page-header clearfix">
        <h1 class="pull-left">小组设置</h1>
    </div>
    <form class="form-horizontal" method="post" id="member-zone-form" novalidate>

<div class="row form-group">
  <div class="col-md-2 control-label">
    <label >首页显示小组信息</label>
  </div>
  <div class="controls col-md-7 radios">
    <@radios 'group_show' {'1':'显示', '0':'不显示'} setting('group.group_show', '1') />
    <div class="help-block">此功能开关只针对默认主题、默认主题B、清秋主题。</div>
  </div>
</div>

<div class="row form-group">
  <div class="col-md-2 control-label">
    <label >最热话题的时间范围</label>
  </div>
  <div class="controls col-md-7 radios">
    <@radios 'threadTime_range'  {'1':'天', '7':'周','14':'两周','30':'月'} setting('group.threadTime_range', '7') />
    <div class="help-block"></div>
  </div>
</div>

<div class="row form-group">
  <div class="col-md-2 control-label"></div>
  <div class="controls col-md-8">
    <button type="submit" class="btn btn-primary">提交</button>
  </div>
</div>

<input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
</form>
</#macro>
