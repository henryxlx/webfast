<#assign script_controller = 'default/message' />
<#include '/layout.ftl'>

<#assign typeTitles = {'info':'提示信息', 'warning':'警告信息', 'error':'错误提示'} />
<#if title??>
<#else>
    <#assign title = typeTitles[type] />
</#if>
<#macro blockTitle>${title} - ${blockTitleParent}</#macro>

<#macro blockHeadScripts>${script!}</#macro>

<#macro blockContent>

<div id="page-message-container" class="page-message-container" data-goto="${gotoUrl!}" data-duration=${duration!}>
  <div class="page-message-panel">
    <div class="page-message-heading">
      <h2 class="page-message-title">${title!}</h2>
    </div>
    <div class="page-message-body">${message!}</div>
  </div>
</div>
</#macro>